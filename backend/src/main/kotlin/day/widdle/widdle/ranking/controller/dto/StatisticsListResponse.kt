package day.widdle.widdle.ranking.controller.dto

import day.widdle.widdle.ranking.service.dto.StatisticsDto

data class StatisticsListResponse(
    val rankings: List<StatisticsDto>,
    val me: StatisticsDto?
)
