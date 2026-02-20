package day.widdle.widdle.ranking.service.dto

import day.widdle.widdle.ranking.controller.dto.StatisticsListResponse

data class StatisticsListDto(
    val summary: RankSummaryDto,
    val rankings: List<StatisticsDto>,
    val me: StatisticsDto?
)

fun StatisticsListDto.toStatisticsListResponse() = StatisticsListResponse(
    summary = this.summary,
    rankings = this.rankings,
    me = this.me
)
