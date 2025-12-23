package day.widdle.widdle.board.service.dto

import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.domain.vo.Statistics

data class StatisticsUpdateDto(
    val id: BoardId,
    val statistics: Statistics
)
