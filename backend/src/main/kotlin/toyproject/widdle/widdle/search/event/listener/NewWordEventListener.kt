package toyproject.widdle.widdle.search.event.listener

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.search.event.NewWordEvent
import toyproject.widdle.widdle.word.service.WordTransactionalService

@Component
class NewWordEventListener(
    private val wordTransactionalService: WordTransactionalService
) {
    private val log = logger()

    @Async
    @EventListener
    fun processNewWordEvent(event: NewWordEvent) {
        wordTransactionalService.save(event.word, event.jamo, event.isKorean)
    }
}