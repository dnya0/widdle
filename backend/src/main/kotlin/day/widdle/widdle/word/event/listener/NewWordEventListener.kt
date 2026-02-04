package day.widdle.widdle.word.event.listener

import day.widdle.widdle.word.event.NewWordEvent
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.word.service.WordService
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component

@Component
class NewWordEventListener(
    private val wordService: WordService
) {

    private val log by loggerDelegate()

    @Async
    @EventListener
    fun processNewWordEvent(event: NewWordEvent) {
        log.info("New word event: $event")
        wordService.createWordIfAbsentTx(event.word, event.jamo, event.isKorean)
    }
}
