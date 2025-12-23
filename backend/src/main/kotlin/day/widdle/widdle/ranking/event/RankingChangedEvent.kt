package day.widdle.widdle.ranking.event

import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.global.event.WiddleEvent

data class RankingChangedEvent(
    val id: RankingId,
    val isKorean: Boolean
) : WiddleEvent
