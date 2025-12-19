package day.widdle.widdle.board.service

import day.widdle.widdle.board.domain.Board
import day.widdle.widdle.board.domain.BoardRepository
import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.service.dto.StatisticsDto
import day.widdle.widdle.board.service.dto.StatisticsListDto
import day.widdle.widdle.board.service.dto.StatisticsSaveDto
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.toTimeRange
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {

    @Cacheable(value = ["rankings"], key = "#id")
    fun getRankingStatistics(id: String, isKorean: Boolean): StatisticsListDto {
        val now = getToday()
        val boardId = BoardId(id)

        return StatisticsListDto(
            rankings = boardRepository.findDailyTopRankings(now, isKorean),
            me = boardRepository.findMyDailyStatus(boardId, now, isKorean)
        )
    }

    @Transactional
    fun save(dto: StatisticsSaveDto) {
        if (!boardRepository.existsById(dto.id)) {
            return
        }
        val board = Board(dto.id, dto.nickname, dto.statistics, dto.isKorean)
        boardRepository.save(board)
    }


    private fun BoardRepository.findDailyTopRankings(date: LocalDate, isKorean: Boolean): List<StatisticsDto> {
        val (start, end) = date.toTimeRange()
        return this.findallbymodifiedatbetweenorderbystatisticsTodayPlaytimeAsc(start, end, isKorean)
            .mapIndexed { index, board -> StatisticsDto.of(board, index + 1) }
    }

    private fun BoardRepository.findMyDailyStatus(
        boardId: BoardId,
        date: LocalDate,
        isKorean: Boolean
    ): StatisticsDto? {
        val (start, end) = date.toTimeRange()
        return this.findMyBoardWithRanking(boardId, start, end, isKorean)
            ?.let { StatisticsDto.of(it.board, it.ranking.toInt()) }
    }

}
