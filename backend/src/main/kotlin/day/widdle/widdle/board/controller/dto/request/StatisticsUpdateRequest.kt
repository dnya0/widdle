package day.widdle.widdle.board.controller.dto.request

import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.domain.vo.Statistics
import day.widdle.widdle.board.service.dto.StatisticsUpdateDto

data class StatisticsUpdateRequest(
    val totalStreak: Int,
    val successRate: Int,
    val currentStreak: Int,
    val bestStreak: Int,
    val todayPlaytime: Long,
) {
    fun toDto(id: String): StatisticsUpdateDto = StatisticsUpdateDto(
        id = BoardId(id),
        statistics = Statistics(
            totalStreak = totalStreak,
            successRate = successRate,
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            todayPlaytime = todayPlaytime
        )
    )
}
