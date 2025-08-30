package toyproject.widdle.widdle.event.listener

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import toyproject.widdle.widdle.event.WordSavedEvent

@Component
class WordSavedEventListener(private val cacheManager: CacheManager) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun afterCommit(e: WordSavedEvent) {
        cacheManager.getCache("hasWord")?.evict(e.jamo.toString())
    }
}