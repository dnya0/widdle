package day.widdle.widdle.board.service

import day.widdle.widdle.board.domain.Board
import day.widdle.widdle.board.domain.BoardRepository
import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.event.RankingChangedEvent
import day.widdle.widdle.board.service.dto.StatisticsDto
import day.widdle.widdle.board.service.dto.StatisticsListDto
import day.widdle.widdle.board.service.dto.StatisticsUpsertDto
import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.toTimeRange
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import kotlin.jvm.optionals.getOrNull

@Service
class BoardService(
    private val boardRepository: BoardRepository,
    private val boardRankingReader: BoardRankingReader,
    private val widdleEventPublisher: WiddleEventPublisher
) {

    fun getRankingStatistics(id: String, isKorean: Boolean, date: LocalDate = getToday()): StatisticsListDto {
        val boardId = BoardId(id)

        return StatisticsListDto(
            rankings = boardRankingReader.findDailyTopRankings(date, isKorean),
            me = boardRepository.findMyDailyStatus(boardId, date, isKorean)
        )
    }

    @Transactional
    fun upsert(dto: StatisticsUpsertDto) {
        val board = boardRepository.findById(dto.id).getOrNull()?.apply {
            this.updateNickname(dto.nickname)
            this.updateStatistics(dto.statistics)
        } ?: Board(dto.id, dto.nickname, dto.statistics, dto.isKorean)

        val currentTop10 = boardRankingReader.findDailyTopRankings(getToday(), dto.isKorean)
        boardRepository.save(board)

        if (affectsTopRankings(dto, currentTop10)) {
            widdleEventPublisher.publishEvent(RankingChangedEvent(dto.id, dto.isKorean))
        }
    }

    private fun affectsTopRankings(
        dto: StatisticsUpsertDto,
        currentTop10: List<StatisticsDto>
    ): Boolean {
        if (currentTop10.any { it.id == dto.id.value }) return true
        if (currentTop10.size < 10) return true

        val tenthPlaytime = currentTop10.getOrNull(9)?.playtime
        return tenthPlaytime != null && dto.statistics.todayPlaytime < tenthPlaytime
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
