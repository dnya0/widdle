package toyproject.widdle.widdle.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import toyproject.widdle.widdle.controller.dto.WordResponse
import toyproject.widdle.widdle.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.controller.dto.toResponseDto
import toyproject.widdle.widdle.domain.WordRepository
import toyproject.widdle.widdle.exception.WiddleException
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.support.JamoSplitter.splitToJamoOrChar
import toyproject.widdle.widdle.support.getToday
import java.time.LocalDate

@Service
class WordService(
    private val wordRepository: WordRepository,
    private val wordTransactionalService: WordTransactionalService
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

    @Cacheable(value = ["hasWord"], key = "#word.toString()")
    fun hasWord(word: List<String>): Boolean = wordRepository.existsByWordJamo(word.toTypedArray())

    fun save(request: WordSaveRequest): String {
        if (wordRepository.existsByWordText(request.word))
            throw WiddleException(if (request.isKorean) "이미 존재하는 단어입니다." else "Already exists.")

        val word = request.word.uppercase()
        val wordJamo = request.jamo ?: splitToJamoOrChar(word, request.isKorean)
        return wordTransactionalService.save(word, wordJamo, request.isKorean)
    }

    private fun indexFor(date: LocalDate, size: Int): Int = runCatching {
        val seed = date.toString().hashCode()
        val positive = if (seed == Int.MIN_VALUE) 0 else kotlin.math.abs(seed)
        positive % size
    }.getOrElse {
        throw WiddleException("단어가 존재하지 않습니다.", it)
    }

}