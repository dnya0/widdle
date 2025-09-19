package toyproject.widdle.widdle.search.service

import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import toyproject.widdle.widdle.search.event.NewWordEvent

@Service
@Transactional
class SearchTransactionalService(
    private val publisher: ApplicationEventPublisher
) {

    fun publish(word: String, wordJamo: List<String>, isKorean: Boolean) {
        publisher.publishEvent(NewWordEvent(word, wordJamo, isKorean))
    }
}