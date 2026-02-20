package day.widdle.widdle.word.service

import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.API_FAILURE
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.CORRECT
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.CORRECTED
import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.containsHangul
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.indexFor
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.global.support.toJamoList
import day.widdle.widdle.global.support.toUpperCaseIfEnglish
import day.widdle.widdle.global.transaction.helper.Tx
import day.widdle.widdle.search.service.SearchService
import day.widdle.widdle.word.domain.Word
import day.widdle.widdle.word.domain.WordRepository
import day.widdle.widdle.word.domain.vo.WordId
import day.widdle.widdle.word.domain.vo.WordInfo
import day.widdle.widdle.word.event.WordSavedEvent
import day.widdle.widdle.word.service.dto.DailyWordDto
import day.widdle.widdle.word.service.dto.WordSaveDto
import day.widdle.widdle.word.service.dto.toDto
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class WordService(
    private val wordRepository: WordRepository,
    private val searchService: SearchService,
    private val publisher: WiddleEventPublisher,
    @param:Qualifier("bareunSpellChecker") private val checker: KoreanSpellChecker
) {

    private val log by loggerDelegate()

    @Cacheable(value = ["dailyWordCache"], key = "#date.toString() + ':' + #isKr", sync = true)
    fun getDailyWord(isKr: Boolean, date: LocalDate = getToday()): DailyWordDto {
        log.info("데일리 단어가 있는지 확인합니다. isKr=$isKr, date=$date")
        wordRepository.findByUsedDateByAndKoreanIs(date, isKr)?.let {
            log.info("이미 선택된 단어가 있습니다. wordId=${it}")
            return it.toDto()
        }

        log.info("새로운 단어를 선택합니다. isKr=$isKr, date=$date")
        val list = wordRepository.findAllByNotUsedWord(isKr)
        val idx = indexFor(date, list.size)

        Tx.writable { list[idx].use() }
        return list[idx].toDto()
    }

    fun use(id: String) = Tx.writable {
        wordRepository.findByIdOrNull(WordId(id))?.use()
            ?: throw WiddleException("단어가 존재하지 않습니다.")
    }

    @Cacheable(value = ["hasWord"], key = "#normalizedWord + ':' + #wordJamo.toString()")
    suspend fun hasWord(normalizedWord: String, wordJamo: List<String>): Boolean = withContext(MDCContext()) {
        log.info("단어 조회 요청 들어옴 word=$normalizedWord, jamo=$wordJamo")

        return@withContext if (!normalizedWord.containsHangul()) {
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

    fun createWordFromDtoTx(dto: WordSaveDto): String {
        val word = dto.word.toUpperCaseIfEnglish()
        val wordJamo = dto.jamo ?: word.toJamoList()
        return createWordAndPublishTx(word, wordJamo, dto.isKorean)
    }

    fun createWordAndPublishTx(wordText: String, jamo: List<String>, isKorean: Boolean): String = Tx.writable {
        if (!persistIfAbsentCore(wordText, jamo, isKorean))
            throw WiddleException(if (isKorean) "이미 존재하는 단어입니다." else "Already exists.")
        publisher.publishEvent(WordSavedEvent(wordText, jamo))
        "successfully saved $wordText"
    }

    fun createWordIfAbsentTx(wordText: String, jamo: List<String>, isKorean: Boolean) = Tx.writable {
        persistIfAbsentCore(wordText, jamo, isKorean)
    }

    private fun String.existInDatabase(): Boolean = wordRepository.existsByWordInfoWordText(this)

    /**
     * public 엔트리포인트에서 트랜잭션 경계 잡을 것
     */
    private fun persistIfAbsentCore(wordText: String, jamo: List<String>, isKorean: Boolean): Boolean {
        if (wordText.existInDatabase()) return false
        wordRepository.save(Word(wordInfo = WordInfo(wordText, jamo, isKorean)))
        return true
    }
}
