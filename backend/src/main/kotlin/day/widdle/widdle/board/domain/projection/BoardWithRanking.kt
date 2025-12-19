package day.widdle.widdle.board.domain.projection

import day.widdle.widdle.board.domain.Board

interface BoardWithRanking {
    val board: Board
    val ranking: Long
}
