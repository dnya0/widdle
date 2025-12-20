package day.widdle.widdle.board.domain

import day.widdle.widdle.board.domain.projection.BoardWithRanking
import day.widdle.widdle.board.domain.vo.BoardId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository : JpaRepository<Board, BoardId> {

    @Query(
        """
        select b from Board b 
        where b.modifiedAt >= :start and b.modifiedAt < :end and b.isKorean = :isKorean
        order by b.statistics.todayPlaytime asc
        limit 10
    """
    )
    fun findallbymodifiedatbetweenorderbystatisticsTodayPlaytimeAsc(start: Long, end: Long, isKorean: Boolean): List<Board>

    @Query(
        """
        SELECT 
            b as board, 
            (SELECT COUNT(b2) + 1
             FROM Board b2 
             WHERE b2.modifiedAt >= :start 
               AND b2.modifiedAt < :end 
               AND b.isKorean = :isKorean
               AND b2.statistics.todayPlaytime > b.statistics.todayPlaytime) as ranking
        FROM Board b
        WHERE b.id = :boardId
          AND b.isKorean = :isKorean
          AND b.modifiedAt >= :start 
          AND b.modifiedAt < :end
    """
    )
    fun findMyBoardWithRanking(
        @Param("boardId") boardId: BoardId,
        @Param("start") start: Long,
        @Param("end") end: Long,
        @Param("isKorean") isKorean: Boolean
    ): BoardWithRanking?

}
