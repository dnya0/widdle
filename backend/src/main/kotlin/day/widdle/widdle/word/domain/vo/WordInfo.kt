package day.widdle.widdle.word.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Embeddable
data class WordInfo(

    @Column(length = 50, unique = true, nullable = false)
    val wordText: String,

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "word_jamo", columnDefinition = "text[]", nullable = false)
    val wordJamo: List<String>,

    @Column(name = "is_korean")
    val isKorean: Boolean = true

) {

    init {
        require(wordText.isNotBlank()) { "단어는 비어있을 수 없습니다" }

        val (expectedLength, message) = if (isKorean) {
            6 to "단어는 6 글자여야 합니다. (현재 ${wordJamo.size} 글자)"
        } else {
            5 to "Words must be exactly 5 characters (currently ${wordJamo.size} characters)."
        }

        require(wordJamo.size == expectedLength) { message }
    }

}
