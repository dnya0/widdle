package day.widdle.widdle.ranking.domain

import day.widdle.widdle.ranking.domain.vo.RankingId
import day.widdle.widdle.ranking.domain.vo.Nickname
import day.widdle.widdle.ranking.domain.vo.Statistics
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.EntityListeners
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@Entity
@Table(name = "ranking")
@EntityListeners(AuditingEntityListener::class)
class Ranking(
    @EmbeddedId
    val id: RankingId = RankingId(),

    @Embedded
    var nickname: Nickname,

    @Embedded
    var statistics: Statistics,

    @Column(name = "is_korean")
    val isKorean: Boolean = true,

    @CreatedDate
    @Column(name = "created_at")
    val createdAt: Long = 0L,

    @LastModifiedDate
    @Column(name = "modified_at")
    var modifiedAt: Long = 0L
) {

    fun updateNickname(nickname: Nickname) {
        this.nickname = nickname
    }

    fun updateStatistics(statistics: Statistics) {
        this.statistics = Statistics(
            totalStreak = statistics.totalStreak,
            successRate = statistics.successRate,
            currentStreak = statistics.currentStreak,
            bestStreak = statistics.bestStreak,
            todayPlaytime = statistics.todayPlaytime,
            totalPlaytime = statistics.todayPlaytime + this.statistics.totalPlaytime,
        )
    }
}
