package day.widdle.widdle.ranking.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class RankingId(
    @Column(name = "id", length = 100, unique = true, nullable = false)
    val value: String = UUID.randomUUID().toString().replace("-", "")
) {
    init {
        require(value.isNotBlank()) { "RankingId 빈 칸일 수 없습니다." }
        require(value.length <= 100) { "RankingId 길이는 100자를 초과할 수 없습니다." }
    }
}
