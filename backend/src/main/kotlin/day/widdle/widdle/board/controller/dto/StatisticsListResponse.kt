package day.widdle.widdle.board.controller.dto

import day.widdle.widdle.board.service.dto.StatisticsDto

data class StatisticsListResponse(
    val rankings: List<StatisticsDto>,
    val me: StatisticsDto?
)
