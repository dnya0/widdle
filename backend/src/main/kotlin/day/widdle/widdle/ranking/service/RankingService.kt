package day.widdle.widdle.ranking.service

import day.widdle.widdle.global.event.publisher.WiddleEventPublisher
import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.toTimeRange
import day.widdle.widdle.ranking.domain.Ranking
import day.widdle.widdle.ranking.domain.RankingRepository
import day.widdle.widdle.ranking.domain.exception.DuplicateNicknameException
import day.widdle.widdle.ranking.domain.exception.RankingIdNotFoundException
import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.ranking.event.RankingChangedEvent
import day.widdle.widdle.ranking.service.dto.StatisticsDto
import day.widdle.widdle.ranking.service.dto.StatisticsListDto
import day.widdle.widdle.ranking.service.dto.StatisticsSaveDto
import day.widdle.widdle.ranking.service.dto.StatisticsUpdateDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class RankingService(
    private val rankingRepository: RankingRepository,
    private val boardRankingReader: BoardRankingReader,
    private val widdleEventPublisher: WiddleEventPublisher
) {

    fun getRankingStatistics(id: String, isKorean: Boolean, date: LocalDate = getToday()): StatisticsListDto {
        val rankingId = RankingId(id)

        return StatisticsListDto(
            rankings = boardRankingReader.findDailyTopRankings(date, isKorean),
            me = rankingRepository.findMyDailyStatus(rankingId, date, isKorean)
        )
    }

    @Transactional
    fun save(dto: StatisticsSaveDto): String {
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
        return ranking.id.value
    }

    @Transactional
    fun update(dto: StatisticsUpdateDto): String {
        val ranking = rankingRepository.findByDeviceId(dto.deviceId) ?: throw RankingIdNotFoundException()
        val currentTop10 = boardRankingReader.findDailyTopRankings(getToday(), ranking.isKorean)
        ranking.updateStatistics(dto.statistics)

        if (affectsTopRankings(ranking.statistics.todayPlaytime, currentTop10, ranking.id)) {
            widdleEventPublisher.publishEvent(RankingChangedEvent(ranking.id, ranking.isKorean))
        }

        return ranking.id.value
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
