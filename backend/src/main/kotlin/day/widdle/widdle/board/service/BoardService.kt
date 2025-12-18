package day.widdle.widdle.board.service

import day.widdle.widdle.board.domain.BoardRepository
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val boardRepository: BoardRepository
) {
}
