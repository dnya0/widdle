package day.widdle.widdle.event.publisher

import day.widdle.widdle.event.WiddleEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Component

@Component
class WiddleEventPublisher(
    private val publisher: ApplicationEventPublisher
) {

    fun publishEvent(flag: Boolean, widdleEvent: WiddleEvent) {
        if (flag) {
            publishEvent(widdleEvent)
        }
    }

    fun publishEvent(widdleEvent: WiddleEvent) {
        publisher.publishEvent(widdleEvent)
    }

}