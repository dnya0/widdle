package day.widdle.widdle.ranking.service.dto

import day.widdle.widdle.ranking.domain.vo.DeviceId
import day.widdle.widdle.ranking.domain.vo.Statistics

data class StatisticsUpdateDto(
    val deviceId: DeviceId,
    val statistics: Statistics
)
