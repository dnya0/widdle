package day.widdle.widdle.ranking.service.dto

import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.ranking.domain.vo.Statistics

data class StatisticsUpdateDto(
    val id: RankingId,
    val statistics: Statistics
)
