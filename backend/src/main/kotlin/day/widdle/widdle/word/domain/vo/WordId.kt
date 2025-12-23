package day.widdle.widdle.word.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class WordId(
    @Column(name = "id", length = 100, unique = true, nullable = false)
    val value: String = UUID.randomUUID().toString().replace("-", "")
) {
    init {
        require(value.isNotBlank()) { "WordId cannot be blank" }
        require(value.length <= 100) { "WordId length must not exceed 100 characters" }
    }
}
