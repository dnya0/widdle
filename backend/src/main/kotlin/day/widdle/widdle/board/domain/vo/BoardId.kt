package day.widdle.widdle.board.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class BoardId(

    @Column(name = "id", nullable = false, unique = true)
    val value: String

) {

    init {
        require(value.isNotBlank()) { "id는 빈 칸일 수 없습니다." }
    }

}
