package day.widdle.widdle.board.service

import day.widdle.widdle.board.domain.BoardRepository
import day.widdle.widdle.board.service.dto.StatisticsDto
import day.widdle.widdle.global.support.toTimeRange
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class BoardRankingReader(
    private val boardRepository: BoardRepository,
) {

    @Cacheable(value = ["topRankings"], key = "#date + ':' + #isKorean")
    fun findDailyTopRankings(date: LocalDate, isKorean: Boolean): List<StatisticsDto> {
        val (start, end) = date.toTimeRange()
        return boardRepository
            .findAllByModifiedAtBetweenOrderByStatisticsTodayPlaytimeAsc(start, end, isKorean)
            .mapIndexed { index, board -> StatisticsDto.of(board, index + 1) }
    }

}
