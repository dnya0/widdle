package day.widdle.widdle.word.service

import day.widdle.widdle.exception.WiddleException
import day.widdle.widdle.logger.logger
import day.widdle.widdle.search.service.SearchService
import day.widdle.widdle.support.JamoSplitter.splitToJamoOrChar
import day.widdle.widdle.support.getToday
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
    private val searchService: SearchService
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

    @Cacheable(value = ["hasWord"], key = "#wordJamo.toString()")
    suspend fun hasWord(word: String, wordJamo: List<String>): Boolean {
        if (wordRepository.existsByWordText(word)) return true
        return searchService.hasWordInDictionary(word, wordJamo)
    }

    fun save(request: WordSaveRequest): String {
        if (wordRepository.existsByWordText(request.word))
            throw WiddleException(if (request.isKorean) "이미 존재하는 단어입니다." else "Already exists.")

        val word = request.word.uppercase()
        val wordJamo = request.jamo ?: splitToJamoOrChar(word, request.isKorean)
        return wordTransactionalService.saveAndPublish(word, wordJamo, request.isKorean)
    }

    private fun indexFor(date: LocalDate, size: Int): Int = runCatching {
        val seed = date.toString().hashCode()
        val positive = if (seed == Int.MIN_VALUE) 0 else abs(seed)
        positive % size
    }.getOrElse {
        throw WiddleException("단어가 존재하지 않습니다.", it)
    }

}