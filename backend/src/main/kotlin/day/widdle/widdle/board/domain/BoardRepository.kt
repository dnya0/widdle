package day.widdle.widdle.board.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BoardRepository: JpaRepository<Board, String> {
}
