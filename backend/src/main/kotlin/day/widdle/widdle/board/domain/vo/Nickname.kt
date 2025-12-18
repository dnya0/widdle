package day.widdle.widdle.board.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class Nickname(@Column(length = 50) val value: String) {

    init {
        require(value.isNotBlank()) { "닉네임은 비어있을 수 없습니다" }
        require(value.length in 2..50) { "닉네임은 2자 이상 50자 이하여야 합니다" }
    }

}
