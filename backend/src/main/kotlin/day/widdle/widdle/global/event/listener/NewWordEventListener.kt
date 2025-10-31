package day.widdle.widdle.global.event.listener

import day.widdle.widdle.global.event.NewWordEvent
import day.widdle.widdle.global.support.logger
import day.widdle.widdle.word.service.WordTransactionalService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

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
