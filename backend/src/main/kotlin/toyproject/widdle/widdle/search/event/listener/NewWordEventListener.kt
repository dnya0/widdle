package toyproject.widdle.widdle.search.event.listener

import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionalEventListener
import toyproject.widdle.widdle.search.event.NewWordEvent
import toyproject.widdle.widdle.word.controller.dto.WordSaveRequest
import toyproject.widdle.widdle.word.service.WordService

@Component
class NewWordEventListener(
    private val wordService: WordService
) {

    @TransactionalEventListener
    fun processNewWordEvent(event: NewWordEvent) {
        wordService.save(WordSaveRequest(event.word, event.jamo, event.isKorean))
    }
}