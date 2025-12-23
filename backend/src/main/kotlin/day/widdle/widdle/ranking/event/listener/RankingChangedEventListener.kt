package day.widdle.widdle.ranking.event.listener

import day.widdle.widdle.ranking.event.RankingChangedEvent
import day.widdle.widdle.global.support.getToday
import org.springframework.cache.CacheManager
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class RankingChangedEventListener(
    private val cacheManager: CacheManager
) {

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun afterRankSave(e: RankingChangedEvent) {
        cacheManager.getCache("topRankings")?.evict("${getToday()}:${e.isKorean}")
    }

}
