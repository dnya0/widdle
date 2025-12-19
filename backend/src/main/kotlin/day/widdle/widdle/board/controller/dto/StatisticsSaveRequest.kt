package day.widdle.widdle.board.controller.dto

import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.domain.vo.Nickname
import day.widdle.widdle.board.domain.vo.Statistics
import day.widdle.widdle.board.service.dto.StatisticsSaveDto

data class StatisticsSaveRequest(
    val id: String,
    val nickname: String,
    val totalStreak: Int,
    val successRate: Int,
    val currentStreak: Int,
    val bestStreak: Int,
    val todayPlaytime: Long,
    val isKorean: Boolean
) {
    fun toDto(): StatisticsSaveDto = StatisticsSaveDto(
        id = BoardId(id),
        nickname = Nickname(nickname),
        statistics = Statistics(
            totalStreak = totalStreak,
            successRate = successRate,
            currentStreak = currentStreak,
            bestStreak = bestStreak,
            todayPlaytime = todayPlaytime,
            totalPlaytime = todayPlaytime
        ),
        isKorean = isKorean
    )
}
