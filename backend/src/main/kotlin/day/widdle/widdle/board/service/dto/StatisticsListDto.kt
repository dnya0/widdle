package day.widdle.widdle.board.service.dto

import day.widdle.widdle.board.controller.dto.StatisticsListResponse

data class StatisticsListDto(
    val rankings: List<StatisticsDto>,
    val me: StatisticsDto?
)

fun StatisticsListDto.toStatisticsListResponse() = StatisticsListResponse(
    rankings = this.rankings,
    me = this.me
)
