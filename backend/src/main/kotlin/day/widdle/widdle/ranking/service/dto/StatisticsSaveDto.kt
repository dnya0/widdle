package day.widdle.widdle.ranking.service.dto

import day.widdle.widdle.ranking.domain.vo.Nickname
import day.widdle.widdle.ranking.domain.vo.Statistics

data class StatisticsSaveDto(
    val nickname: Nickname,
    val statistics: Statistics,
    val isKorean: Boolean
)
