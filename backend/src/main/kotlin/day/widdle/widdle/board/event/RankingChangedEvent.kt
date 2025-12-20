package day.widdle.widdle.board.event

import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.global.event.WiddleEvent

data class RankingChangedEvent(
    val id: BoardId,
    val isKorean: Boolean
) : WiddleEvent
