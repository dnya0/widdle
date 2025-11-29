package day.widdle.widdle.global.event.listener

import day.widdle.widdle.global.event.NewWordEvent
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.word.service.WordTransactionalService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NewWordEventListener(
    private val wordTransactionalService: WordTransactionalService
) {

    private val log by loggerDelegate()

    @Async
    @EventListener
    fun processNewWordEvent(event: NewWordEvent) {
        log.info("New word event: $event")
        wordTransactionalService.save(event.word, event.jamo, event.isKorean)
    }
}
