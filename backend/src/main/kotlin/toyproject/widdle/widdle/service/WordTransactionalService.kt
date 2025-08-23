package toyproject.widdle.widdle.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import toyproject.widdle.widdle.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.domain.Word
import toyproject.widdle.widdle.domain.WordRepository
import toyproject.widdle.widdle.exception.WiddleException
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.support.JamoSplitter.splitToJamoOrChar

@Service
@Transactional
class WordTransactionalService(
    private val wordRepository: WordRepository
) {
    private val log = logger()

    fun use(word: Word) = word.use()

    fun use(id: String) = wordRepository.findByIdOrNull(id)?.use()
        ?: throw WiddleException("단어가 존재하지 않습니다.")

    fun save(request: WordSaveRequest): String {
        val wordJamo = request.jamo ?: splitToJamoOrChar(request.word, request.isKorean)

        log.info(wordJamo.toString())

        val (expectedLength, message) = if (request.isKorean) {
            6 to "단어는 6 글자여야 합니다. (현재 ${wordJamo.size} 글자)"
        } else {
            5 to "Words must be exactly 5 characters (currently ${wordJamo.size} characters)."
        }

        if (wordJamo.size != expectedLength) {
            throw WiddleException(message)
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
}