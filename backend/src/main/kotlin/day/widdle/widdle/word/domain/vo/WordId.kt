package day.widdle.widdle.word.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.util.UUID

@Embeddable
data class WordId(

    @Column(name = "id", length = 100, unique = true, nullable = false)
    val value: String = UUID.randomUUID().toString().replace("-", "")

)
