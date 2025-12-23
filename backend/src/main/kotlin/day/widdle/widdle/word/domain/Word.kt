package day.widdle.widdle.word.domain

import day.widdle.widdle.global.support.getCurrentDateTime
import day.widdle.widdle.word.domain.vo.WordId
import day.widdle.widdle.word.domain.vo.WordInfo
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "word")
class Word(

    @EmbeddedId
    val id: WordId = WordId(),

    @Embedded
    val wordInfo: WordInfo,

    @Column(name = "is_used", nullable = false)
    var isUsed: Boolean = false,

    @Column(name = "used_date_by")
    var usedDateBy: LocalDate? = null,

    @Column(name = "used_date_by_ts")
    var usedDateByTs: Long? = null

) {

    fun use() {
        val now = getCurrentDateTime()
        this.isUsed = true
        this.usedDateBy = now.date
        this.usedDateByTs = now.timestamp
    }

}
