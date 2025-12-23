package day.widdle.widdle.ranking.domain

import day.widdle.widdle.ranking.domain.projection.BoardWithRanking
import day.widdle.widdle.ranking.domain.vo.DeviceId
import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.ranking.domain.vo.Nickname
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RankingRepository : JpaRepository<Ranking, RankingId> {

    @Query(
        """
        select r from Ranking r
        where r.modifiedAt >= :start and r.modifiedAt < :end and r.isKorean = :isKorean
        order by r.statistics.todayPlaytime asc
        limit 10
    """
    )
    fun findAllByModifiedAtBetweenOrderByStatisticsTodayPlaytimeAsc(
        start: Long,
        end: Long,
        isKorean: Boolean
    ): List<Ranking>

    fun findByDeviceId(deviceId: DeviceId): Ranking?

    @Query(
        """
        SELECT 
            r as ranking, 
            (SELECT COUNT(r2) + 1
             FROM Ranking r2 
             WHERE r2.modifiedAt >= :start 
               AND r2.modifiedAt < :end 
               AND r2.isKorean = :isKorean
               AND r2.statistics.todayPlaytime > r.statistics.todayPlaytime) as rank
        FROM Ranking r
        WHERE r.id = :rankingId
          AND r.isKorean = :isKorean
          AND r.modifiedAt >= :start 
          AND r.modifiedAt < :end
    """
    )
    fun findMyBoardWithRanking(
        @Param("rankingId") rankingId: RankingId,
        @Param("start") start: Long,
        @Param("end") end: Long,
        @Param("isKorean") isKorean: Boolean
    ): BoardWithRanking?

    fun existsByNickname(nickname: Nickname): Boolean
}
