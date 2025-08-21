package toyproject.widdle.widdle.service

import org.springframework.cache.annotation.Cacheable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import toyproject.widdle.widdle.controller.dto.WordRequest
import toyproject.widdle.widdle.controller.dto.WordResponse
import toyproject.widdle.widdle.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.controller.dto.toResponseDto
import toyproject.widdle.widdle.domain.Word
import toyproject.widdle.widdle.domain.WordRepository
import toyproject.widdle.widdle.exception.WiddleException
import toyproject.widdle.widdle.support.JamoSplitter.splitToJamoOrChar
import java.time.LocalDate
import java.time.ZoneId

@Service
class WordService(
    private val wordRepository: WordRepository
) {
    private val zone = ZoneId.of("Asia/Seoul")

    @Cacheable(value = ["dailyWord"], key = "#date.toString()", sync = true)
    fun getDailyWord(isKr: Boolean, date: LocalDate = LocalDate.now(zone)): WordResponse {
        val list = wordRepository.findAllByNotUsedWord(isKr)
        val idx = indexFor(date, list.size)
        return list[idx].toResponseDto()
    }

    fun hasWord(request: WordRequest): Boolean = wordRepository.existsByWordJamo(request.word)

    @Transactional
    fun use(id: String) = wordRepository.findByIdOrNull(id)?.use()
        ?: throw WiddleException("단어가 존재하지 않습니다.")

    @Transactional
    fun save(request: WordSaveRequest): String {
        val wordJamo = request.jamo ?: splitToJamoOrChar(request.word, request.isKorean)

        if (wordJamo.size !in 5..6) {
            throw WiddleException("단어는 5~6 글자여야 합니다. (현재 ${wordJamo.size} 글자)")
        }

        val word = Word(
            wordText = request.word,
            wordJamo = wordJamo,
            length = wordJamo.size,
            isKorean = request.isKorean,
        )

        wordRepository.save(word)
        return "successfully saved $word"
    }

    private fun indexFor(date: LocalDate, size: Int): Int {
        val seed = date.toString().hashCode()
        val positive = if (seed == Int.MIN_VALUE) 0 else kotlin.math.abs(seed)
        return positive % size
    }

}