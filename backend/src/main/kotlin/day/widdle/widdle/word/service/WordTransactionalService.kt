package day.widdle.widdle.word.service

import day.widdle.widdle.global.event.WordSavedEvent
import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.word.domain.Word
import day.widdle.widdle.word.domain.WordRepository
import day.widdle.widdle.word.domain.validator.WordValidator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class WordTransactionalService(
    private val wordRepository: WordRepository,
    private val validator: WordValidator,
    private val publisher: WiddleEventPublisher
) {
    private val log by loggerDelegate()

    fun use(word: Word) = word.use()

    fun use(id: String) = wordRepository.findByIdOrNull(id)?.use()
        ?: throw WiddleException("단어가 존재하지 않습니다.")

    fun saveAndPublish(wordText: String, jamo: List<String>, isKorean: Boolean): String {
        save(wordText, jamo, isKorean)
        publisher.publishEvent(WordSavedEvent(wordText, jamo))
        return "successfully saved $wordText"
    }

    fun save(wordText: String, jamo: List<String>, isKorean: Boolean) {
        validator.validateJamoSize(isKorean, jamo)

        if (wordRepository.existsByWordText(wordText)) {
            log.warn("Attempted to save duplicate word, ignoring: {}", wordText)
            return
        }

        val word = Word(
            wordText = wordText,
            wordJamo = jamo,
            length = jamo.size,
            isKorean = isKorean,
        )

        wordRepository.save(word)
    }
}
