package day.widdle.widdle.word.domain

import day.widdle.widdle.global.support.getToday
import day.widdle.widdle.global.support.now
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "word")
class Word(
    @Id
    @Column(length = 100, unique = true, nullable = false)
    val id: String = UUID.randomUUID().toString().replace("-", ""),

    @Column(length = 50, unique = true, nullable = false)
    val wordText: String,

    @JdbcTypeCode(SqlTypes.ARRAY)
    @Column(name = "word_jamo", columnDefinition = "text[]", nullable = false)
    var wordJamo: List<String> = mutableListOf(),

    @Column(nullable = false)
    val length: Int = 6,

    @Column(nullable = false)
    var isUsed: Boolean = false,

    @Column
    var usedDateBy: LocalDate? = null,

    @Column
    var usedDateByTs: Long? = null,

    @Column
    val isKorean: Boolean = true
) {

    fun use() {
        this.isUsed = true
        this.usedDateBy = getToday()
        this.usedDateByTs = now()
    }

}
