package day.widdle.widdle.ranking.service

import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.toTimeRange
import day.widdle.widdle.global.transaction.helper.Tx
import day.widdle.widdle.ranking.domain.Ranking
import day.widdle.widdle.ranking.domain.RankingRepository
import day.widdle.widdle.ranking.domain.exception.DuplicateNicknameException
import day.widdle.widdle.ranking.domain.exception.RankingIdNotFoundException
import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.ranking.event.RankingChangedEvent
import day.widdle.widdle.ranking.service.dto.RankSummaryDto
import day.widdle.widdle.ranking.service.dto.StatisticsDto
import day.widdle.widdle.ranking.service.dto.StatisticsListDto
import day.widdle.widdle.ranking.service.dto.StatisticsSaveDto
import day.widdle.widdle.ranking.service.dto.StatisticsUpdateDto
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.math.roundToInt

@Service
class RankingService(
    private val rankingRepository: RankingRepository,
    private val boardRankingReader: BoardRankingReader,
    private val widdleEventPublisher: WiddleEventPublisher
) {

    fun getRankingStatistics(
        id: String, isKorean: Boolean, date: LocalDate = getToday()
    ): StatisticsListDto = Tx.readable {
        val rankingId = RankingId(id)
        val (start, end) = date.toTimeRange()

        val totalUser = rankingRepository.countByIsKorean(isKorean).toInt()
        val avgPlaytime = rankingRepository
            .findAverageTodayPlaytime(start, end, isKorean)
            ?.roundToInt() ?: 0

        StatisticsListDto(
            summary = RankSummaryDto(totalUser = totalUser, avgPlaytime = avgPlaytime),
            rankings = boardRankingReader.findDailyTopRankings(date, isKorean),
            me = rankingRepository.findMyDailyStatus(rankingId, date, isKorean)
        )
    }

    fun save(dto: StatisticsSaveDto): String = Tx.writable {
        if (rankingRepository.existsByNickname(dto.nickname)) {
            throw DuplicateNicknameException()
        }

        val currentTop10 = boardRankingReader.findDailyTopRankings(getToday(), dto.isKorean)

        val ranking = Ranking(
            nickname = dto.nickname,
            statistics = dto.statistics,
            deviceId = dto.deviceId,
            isKorean = dto.isKorean
        )
        rankingRepository.save(ranking)

        if (affectsTopRankings(dto.statistics.todayPlaytime, currentTop10, ranking.id)) {
            widdleEventPublisher.publishEvent(RankingChangedEvent(ranking.id, dto.isKorean))
        }
        ranking.id.value
    }

    fun update(dto: StatisticsUpdateDto): String = Tx.writable {
        val ranking = rankingRepository.findByDeviceId(dto.deviceId) ?: throw RankingIdNotFoundException()
        val currentTop10 = boardRankingReader.findDailyTopRankings(getToday(), ranking.isKorean)
        ranking.updateStatistics(dto.statistics)

        if (affectsTopRankings(ranking.statistics.todayPlaytime, currentTop10, ranking.id)) {
            widdleEventPublisher.publishEvent(RankingChangedEvent(ranking.id, ranking.isKorean))
        }

        ranking.id.value
    }

    private fun affectsTopRankings(
        todayPlaytime: Long,
        currentTop10: List<StatisticsDto>,
        rankingId: RankingId
    ): Boolean {
        if (currentTop10.any { it.id == rankingId.value }) return true
        if (currentTop10.size < 10) return true

        val tenthPlaytime = currentTop10.getOrNull(9)?.playtime
        return tenthPlaytime != null && todayPlaytime < tenthPlaytime
    }

    private fun RankingRepository.findMyDailyStatus(
        rankingId: RankingId,
        date: LocalDate,
        isKorean: Boolean
    ): StatisticsDto? {
        val (start, end) = date.toTimeRange()
        return this.findMyBoardWithRanking(rankingId, start, end, isKorean)
            ?.let { StatisticsDto.of(it.ranking, it.rank.toInt()) }
    }

}
