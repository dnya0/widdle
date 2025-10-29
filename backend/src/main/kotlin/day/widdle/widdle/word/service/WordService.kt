package day.widdle.widdle.word.service

import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.API_FAILURE
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.CORRECT
import day.widdle.widdle.correction.service.dto.value.CorrectionStatus.CORRECTED
import day.widdle.widdle.global.event.NewWordEvent
import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.logger
import day.widdle.widdle.search.service.SearchService
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.toJamoList
import day.widdle.widdle.word.controller.dto.WordResponse
import day.widdle.widdle.word.controller.dto.WordSaveRequest
import day.widdle.widdle.word.controller.dto.toResponseDto
import day.widdle.widdle.word.domain.WordRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.math.abs

@Service
class WordService(
    private val wordRepository: WordRepository,
    private val wordTransactionalService: WordTransactionalService,
    private val searchService: SearchService,
    private val checker: KoreanSpellChecker,
    private val publisher: WiddleEventPublisher
) {

    private val log = logger()

    @Cacheable(value = ["dailyWord"], key = "#p1.toString() + ':' + #p0", sync = true)
    fun getDailyWord(isKr: Boolean, date: LocalDate = getToday()): WordResponse {
        wordRepository.findByUsedDateByAndKoreanIs(date, isKr)?.let { return it.toResponseDto() }

        val list = wordRepository.findAllByNotUsedWord(isKr)
        val idx = indexFor(date, list.size)

        wordTransactionalService.use(list[idx])
        return list[idx].toResponseDto()
    }

    fun use(id: String) = wordTransactionalService.use(id)

    @Cacheable(value = ["hasWord"], key = "#word.toUpperCase() + ':' + #wordJamo.toString()")
    suspend fun hasWord(word: String, wordJamo: List<String>): Boolean {
        val correct = checker.correct(word)
        val correctWord = correct.correctWord?.uppercase()

        return when (correct.correctionStatus) {
            CORRECT -> {
                publishNewWordIfAbsent(correctWord)
                true
            }
            CORRECTED -> {
                publishNewWordIfAbsent(correctWord)
                false
            }
            API_FAILURE -> false
        }
    }

    fun save(request: WordSaveRequest): String {
        val word = request.word.uppercase()
        if (wordRepository.existsByWordText(request.word))
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

    private fun publishNewWordIfAbsent(word: String?) {
        word?.let {
            if (!wordRepository.existsByWordText(word)) {
                publisher.publishEvent(NewWordEvent.to(word))
            }
        }
    }

}
