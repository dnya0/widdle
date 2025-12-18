package day.widdle.widdle.word.service

import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.API_FAILURE
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.CORRECT
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.CORRECTED
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.isKorean
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.global.support.toJamoList
import day.widdle.widdle.global.support.toUpperCaseIfEnglish
import day.widdle.widdle.search.service.SearchService
import day.widdle.widdle.word.controller.dto.WordResponse
import day.widdle.widdle.word.controller.dto.WordSaveRequest
import day.widdle.widdle.word.controller.dto.toResponseDto
import day.widdle.widdle.word.domain.WordRepository
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.math.abs

@Service
class WordService(
    private val wordRepository: WordRepository,
    private val wordTransactionalService: WordTransactionalService,
    private val searchService: SearchService,
    @param:Qualifier("bareunSpellChecker") private val checker: KoreanSpellChecker
) {

    private val log by loggerDelegate()

    @Cacheable(value = ["dailyWord"], key = "#p1.toString() + ':' + #p0", sync = true)
    fun getDailyWord(isKr: Boolean, date: LocalDate = getToday()): WordResponse {
        log.info("데일리 단어가 있는지 확인합니다. isKr=$isKr, date=$date")
        wordRepository.findByUsedDateByAndKoreanIs(date, isKr)?.let {
            log.info("이미 선택된 단어가 있습니다. wordId=${it}")
            return it.toResponseDto()
        }

        log.info("새로운 단어를 선택합니다. isKr=$isKr, date=$date")
        val list = wordRepository.findAllByNotUsedWord(isKr)
        val idx = indexFor(date, list.size)

        wordTransactionalService.use(list[idx])
        return list[idx].toResponseDto()
    }

    fun use(id: String) = wordTransactionalService.use(id)

    @Cacheable(value = ["hasWord"], key = "#normalizedWord + ':' + #wordJamo.toString()")
    suspend fun hasWord(normalizedWord: String, wordJamo: List<String>): Boolean = withContext(MDCContext()) {
        log.info("단어 조회 요청 들어옴 word=$normalizedWord, jamo=$wordJamo")

        return@withContext if (!normalizedWord.isKorean()) {
            hasEnglishWord(normalizedWord, wordJamo)
        } else {
            hasKoreanWord(normalizedWord, wordJamo)
        }
    }

    suspend fun hasEnglishWord(word: String, wordJamo: List<String>): Boolean {
        if (word.existInDatabase()) {
            return true
        }
        return searchService.hasWordInDictionary(word, wordJamo)
    }

    suspend fun hasKoreanWord(word: String, wordJamo: List<String>): Boolean {
        val correct = checker.correct(word, wordJamo)
        val correctWord = correct.correctWord?.toUpperCaseIfEnglish() ?: return false

        return when (correct.correctionStatus) {
            CORRECT -> {
                if (correctWord.existInDatabase()) true
                else searchService.hasWordInDictionary(correctWord, wordJamo)
            }

            CORRECTED -> {
                // 수정된 단어가 사전에 있으면 DB에 저장하기 위해 호출 (이벤트 발행)
                searchService.hasWordInDictionary(correctWord, wordJamo)
                false // 원래 단어가 틀렸으므로 false 반환
            }

            API_FAILURE -> false
        }
    }

    fun save(request: WordSaveRequest): String {
        val word = request.word.toUpperCaseIfEnglish()
        if (word.existInDatabase())
            throw WiddleException(if (request.isKorean) "이미 존재하는 단어입니다." else "Already exists.")

        val wordJamo = request.jamo ?: word.toJamoList()
        return wordTransactionalService.saveAndPublish(word, wordJamo, request.isKorean)
    }

    private fun indexFor(date: LocalDate, size: Int): Int = runCatching {
        val seed = date.toString().hashCode()
        val positive = if (seed == Int.MIN_VALUE) 0 else abs(seed)
        positive % size
    }.getOrElse {
        throw WiddleException("단어가 존재하지 않습니다.", it)
    }

    private fun String.existInDatabase(): Boolean = wordRepository.existsByWordInfoWordText(this)

}
