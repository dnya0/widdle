package day.widdle.widdle.board.service

import day.widdle.widdle.board.domain.Board
import day.widdle.widdle.board.domain.BoardRepository
import day.widdle.widdle.board.domain.exception.BoardIdNotFoundException
import day.widdle.widdle.board.domain.exception.DuplicateNicknameException
import day.widdle.widdle.board.domain.vo.BoardId
import day.widdle.widdle.board.event.RankingChangedEvent
import day.widdle.widdle.board.service.dto.StatisticsDto
import day.widdle.widdle.board.service.dto.StatisticsListDto
import day.widdle.widdle.board.service.dto.StatisticsSaveDto
import day.widdle.widdle.board.service.dto.StatisticsUpdateDto
import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.toTimeRange
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

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
    fun save(dto: StatisticsSaveDto): String {
        if (boardRepository.existsByNickname(dto.nickname)) {
            throw DuplicateNicknameException()
        }

        val currentTop10 = boardRankingReader.findDailyTopRankings(getToday(), dto.isKorean)

        val board = Board(nickname = dto.nickname, statistics = dto.statistics, isKorean = dto.isKorean)
        boardRepository.save(board)

        if (affectsTopRankings(dto.statistics.todayPlaytime, currentTop10, board.id)) {
            widdleEventPublisher.publishEvent(RankingChangedEvent(board.id, dto.isKorean))
        }
        return board.id.value
    }

    @Transactional
    fun update(dto: StatisticsUpdateDto): String {
        val board = boardRepository.findByIdOrNull(dto.id) ?: throw BoardIdNotFoundException()
        val currentTop10 = boardRankingReader.findDailyTopRankings(getToday(), board.isKorean)
        board.updateStatistics(dto.statistics)

        if (affectsTopRankings(board.statistics.todayPlaytime, currentTop10, board.id)) {
            widdleEventPublisher.publishEvent(RankingChangedEvent(board.id, board.isKorean))
        }

        return board.id.value
    }

    private fun affectsTopRankings(
        todayPlaytime: Long,
        currentTop10: List<StatisticsDto>,
        boardId: BoardId
    ): Boolean {
        if (currentTop10.any { it.id == boardId.value }) return true
        if (currentTop10.size < 10) return true

        val tenthPlaytime = currentTop10.getOrNull(9)?.playtime
        return tenthPlaytime != null && todayPlaytime < tenthPlaytime
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
