package day.widdle.widdle.ranking.domain.vo

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import org.hibernate.annotations.Comment

@Embeddable
data class Statistics(

    @Column(name = "total_streak")
    @Comment("전체 도전")
    val totalStreak: Int,

    @Column(name = "success_rate")
    @Comment("정답률")
    val successRate: Int,

    @Column(name = "current_streak")
    @Comment("최근 연속 정답")
    val currentStreak: Int,

    @Column(name = "best_streak")
    @Comment("최다 연속 정답")
    val bestStreak: Int,

    @Column(name = "today_playtime")
    @Comment("오늘 플레이 시간")
    val todayPlaytime: Long = 0L,

    @Column(name = "total_playtime")
    @Comment("전체 총 플레이 시간")
    var totalPlaytime: Long = 0L

) {

    init {
        require(successRate in 0..100) { "정답률은 0 이상 100 이하여야 합니다." }
        require(totalStreak >= 0) { "전체 도전은 0 이상이어야 합니다." }
        require(currentStreak >= 0) { "최근 연속 정답은 0 이상이어야 합니다." }
        require(bestStreak >= 0) { "최다 연속 정답은 0 이상이어야 합니다." }
        require(todayPlaytime >= 0) { "게임 플레이 시간은 0 이상이어야 합니다." }
        require(totalPlaytime >= 0) { "게임 플레이 시간은 0 이상이어야 합니다." }
    }

}
