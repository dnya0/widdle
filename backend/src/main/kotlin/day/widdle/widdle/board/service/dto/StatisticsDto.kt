package day.widdle.widdle.board.service.dto

import day.widdle.widdle.board.domain.Board

data class StatisticsDto(
    val rank: Int,
    val nickname: String,
    val playtime: Long
) {

    companion object {
        fun of(board: Board, index: Int): StatisticsDto = StatisticsDto(
            rank = index,
            nickname = board.nickname.value,
            playtime = board.statistics.todayPlaytime
        )
    }

}
