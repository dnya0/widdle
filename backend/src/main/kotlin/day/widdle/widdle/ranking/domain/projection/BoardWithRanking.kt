package day.widdle.widdle.ranking.domain.projection

import day.widdle.widdle.ranking.domain.Ranking

interface BoardWithRanking {
    val ranking: Ranking
    val rank: Long
}
