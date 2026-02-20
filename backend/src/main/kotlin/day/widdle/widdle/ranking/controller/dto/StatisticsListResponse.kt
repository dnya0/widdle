package day.widdle.widdle.ranking.controller.dto

import day.widdle.widdle.ranking.service.dto.RankSummaryDto
import day.widdle.widdle.ranking.service.dto.StatisticsDto

data class StatisticsListResponse(
    val summary: RankSummaryDto,
    val rankings: List<StatisticsDto>,
    val me: StatisticsDto?
)
