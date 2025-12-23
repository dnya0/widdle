package day.widdle.widdle.ranking.service

import day.widdle.widdle.ranking.domain.RankingRepository
import day.widdle.widdle.ranking.service.dto.StatisticsDto
import day.widdle.widdle.global.support.toTimeRange
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class BoardRankingReader(
    private val rankingRepository: RankingRepository,
) {

    @Cacheable(value = ["topRankings"], key = "#date + ':' + #isKorean")
    fun findDailyTopRankings(date: LocalDate, isKorean: Boolean): List<StatisticsDto> {
        val (start, end) = date.toTimeRange()
        return rankingRepository
            .findAllByModifiedAtBetweenOrderByStatisticsTodayPlaytimeAsc(start, end, isKorean)
            .mapIndexed { index, ranking -> StatisticsDto.of(ranking, index + 1) }
    }

}
