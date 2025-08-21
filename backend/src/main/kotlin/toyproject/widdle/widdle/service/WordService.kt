package toyproject.widdle.widdle.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import toyproject.widdle.widdle.controller.dto.WordResponse
import toyproject.widdle.widdle.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.controller.dto.toResponseDto
import toyproject.widdle.widdle.domain.WordRepository
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.support.getToday
import java.time.LocalDate

@Service
class WordService(
    private val wordRepository: WordRepository,
    private val wordTransactionalService: WordTransactionalService
) {

    private val log = logger()

    @Cacheable(value = ["dailyWord"], key = "#date.toString()", sync = true)
    fun getDailyWord(isKr: Boolean, date: LocalDate = getToday()): WordResponse {
        wordRepository.findByUsedDateBy(date)?.let { return it.toResponseDto() }

        val list = wordRepository.findAllByNotUsedWord(isKr)
        val idx = indexFor(date, list.size)

        wordTransactionalService.use(list[idx])
        return list[idx].toResponseDto()
    }

    fun use(id: String) = wordTransactionalService.use(id)

    fun hasWord(word: List<String>): Boolean {
        log.info(word.toString())
        return wordRepository.existsByWordJamo(word.toTypedArray())
    }

    fun save(request: WordSaveRequest): String = wordTransactionalService.save(request)

    private fun indexFor(date: LocalDate, size: Int): Int {
        val seed = date.toString().hashCode()
        val positive = if (seed == Int.MIN_VALUE) 0 else kotlin.math.abs(seed)
        return positive % size
    }

}