package day.widdle.widdle.board.domain

import day.widdle.widdle.board.domain.vo.BoardId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, BoardId> {
}
