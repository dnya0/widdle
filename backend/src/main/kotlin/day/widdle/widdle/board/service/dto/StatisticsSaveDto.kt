package day.widdle.widdle.board.service.dto

import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.domain.vo.Nickname
import day.widdle.widdle.board.domain.vo.Statistics

data class StatisticsSaveDto(
    val id: BoardId,
    val nickname: Nickname,
    val statistics: Statistics,
    val isKorean: Boolean
)
