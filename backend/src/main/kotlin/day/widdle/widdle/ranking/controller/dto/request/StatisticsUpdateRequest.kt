package day.widdle.widdle.ranking.controller.dto.request

import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.ranking.domain.vo.Statistics
import day.widdle.widdle.ranking.service.dto.StatisticsUpdateDto

data class StatisticsUpdateRequest(
    val totalStreak: Int,
    val successRate: Int,
    val currentStreak: Int,
    val bestStreak: Int,
    val todayPlaytime: Long,
) {
    fun toDto(id: String): StatisticsUpdateDto = StatisticsUpdateDto(
        id = RankingId(id),
        statistics = Statistics(
            totalStreak = totalStreak,
            successRate = successRate,
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            todayPlaytime = todayPlaytime
        )
    )
}
