package day.widdle.widdle.ranking.service.dto

import day.widdle.widdle.ranking.domain.Ranking

data class StatisticsDto(
    val id: String,
    val rank: Int,
    val nickname: String,
    val playtime: Long
) {

    companion object {
        fun of(ranking: Ranking, index: Int): StatisticsDto = StatisticsDto(
            id = ranking.id.value,
            rank = index,
            nickname = ranking.nickname.value,
            playtime = ranking.statistics.todayPlaytime
        )
    }

}
