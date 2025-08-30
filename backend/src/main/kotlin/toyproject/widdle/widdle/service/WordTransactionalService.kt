package toyproject.widdle.widdle.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import toyproject.widdle.widdle.domain.Word
import toyproject.widdle.widdle.domain.WordRepository
import toyproject.widdle.widdle.domain.validator.WordValidator
import toyproject.widdle.widdle.event.WordSavedEvent
import toyproject.widdle.widdle.exception.WiddleException
import toyproject.widdle.widdle.logger.logger

@Service
@Transactional
class WordTransactionalService(
    private val wordRepository: WordRepository,
    private val validator: WordValidator,
    private val publisher: ApplicationEventPublisher
) {
    private val log = logger()

    fun use(word: Word) = word.use()

    fun use(id: String) = wordRepository.findByIdOrNull(id)?.use()
        ?: throw WiddleException("단어가 존재하지 않습니다.")

    fun save(wordText: String, jamo: List<String>, isKorean: Boolean): String {
        validator.validateJamoSize(isKorean, jamo)

        val word = Word(
            wordText = wordText,
            wordJamo = jamo,
            length = jamo.size,
            isKorean = isKorean,
        )

        wordRepository.save(word)
        publisher.publishEvent(WordSavedEvent(jamo))

        return "successfully saved $word"
    }
}