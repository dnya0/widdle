package day.widdle.widdle.board.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class BoardId(
    @Column(name = "id", length = 100, unique = true, nullable = false)
    val value: String = UUID.randomUUID().toString().replace("-", "")
) {
    init {
        require(value.isNotBlank()) { "BoardId 빈 칸일 수 없습니다." }
        require(value.length <= 100) { "BoardId 길이는 100자를 초과할 수 없습니다." }
    }
}
