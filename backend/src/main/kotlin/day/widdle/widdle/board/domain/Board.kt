package day.widdle.widdle.board.domain

import day.widdle.widdle.global.support.now
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import kotlin.time.ExperimentalTime

@Entity
@Table(name = "board")
@ExperimentalTime
class Board(

    @Id
    val id: String,

    @Column(length = 50)
    var nickname: String,

    @Column(name = "total_streak")
    @Comment("전체 도전")
    var totalStreak: Int,

    @Column(name = "success_rate")
    @Comment("정답률")
    var successRate: Int,

    @Column(name = "current_streak")
    @Comment("최근 연속 정답")
    var currentStreak: Int,

    @Column(name = "best_streak")
    @Comment("최다 연속 정답")
    var bestStreak: Int,

    @Column(name = "is_korean")
    val isKorean: Boolean = true,

    @Column(name = "created_at")
    val createdAt: Long,

    @Column(name = "modified_at")
    var modifiedAt: Long
) {

    fun updateNickname(nickname: String) {
        this.nickname = nickname
        this.modifiedAt = now()
    }

    fun updateStatistics(totalStreak: Int, successRate: Int, currentStreak: Int, bestStreak: Int) {
        this.totalStreak = totalStreak
        this.successRate = successRate
        this.currentStreak = currentStreak
        this.bestStreak = bestStreak
        this.modifiedAt = now()
    }
}
