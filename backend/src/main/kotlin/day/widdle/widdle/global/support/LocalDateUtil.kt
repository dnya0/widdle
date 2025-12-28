package day.widdle.widdle.global.support

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

private val zone = ZoneId.of("Asia/Seoul")

/**
 * 현재 시각의 Instant를 반환한다.
 * - 모든 시간 계산의 단일 소스로 사용
 */
private fun nowInstant(): Instant = Instant.now()

/**
 * 현재 날짜를 반환한다.
 * - 기준 타임존: Asia/Seoul
 * - nowInstant()를 기준으로 계산하여 시간 불일치 방지
 */
fun getToday(): LocalDate = nowInstant().atZone(zone).toLocalDate()

/**
 * 현재 시각을 Unix Timestamp(ms)로 반환한다.
 *
 * - 기준: UTC
 * - 용도: DB 저장용 기준 시간
 * - nowInstant()를 기준으로 계산하여 시간 불일치 방지
 */
fun nowEpochMillis(): Long = nowInstant().toEpochMilli()

/**
 * 현재 날짜와 타임스탬프를 동시에 반환한다.
 * - 단일 시점을 기준으로 date와 timestamp를 계산하여 불일치 방지
 * - 자정 근처에서 발생할 수 있는 날짜/시간 불일치 문제 해결
 */
fun getCurrentDateTime(): CurrentDateTime {
    val instant = nowInstant()
    return CurrentDateTime(
        date = instant.atZone(zone).toLocalDate(),
        timestamp = instant.toEpochMilli()
    )
}

data class CurrentDateTime(
    val date: LocalDate,
    val timestamp: Long
)

/**
 * 해당 날짜의 시작 시각(00:00:00)을
 * Unix Timestamp(ms)로 변환한다.
 *
 * - 기준 타임존: zone (예: Asia/Seoul)
 * - 반환 값: UTC 기준 Epoch Milliseconds
 */
fun LocalDate.startEpochMillis(): Long = this.atStartOfDay(zone).toInstant().toEpochMilli()

/**
 * 해당 날짜의 다음 날 시작 시각(익일 00:00:00)을
 * Unix Timestamp(ms)로 변환한다.
 *
 * - 오늘 날짜 조회 시 [start, end) 범위 계산에 사용
 * - 기준 타임존: zone (예: Asia/Seoul)
 * - 반환 값: UTC 기준 Epoch Milliseconds
 */
fun LocalDate.endEpochMillis(): Long = this.plusDays(1).atStartOfDay(zone).toInstant().toEpochMilli()

/**
 * 특정 날짜의 하루 전체 범위를 UTC 타임스탬프로 반환한다.
 *
 * 예시:
 * - 입력: LocalDate(2025-12-20)
 * - 출력: TimeRange(
 *     start: 2025-12-19T15:00:00Z,  // 2025-12-20 00:00:00 KST
 *     end:   2025-12-20T15:00:00Z   // 2025-12-21 00:00:00 KST
 *   )
 *
 * @param zone 기준 타임존 (예: Asia/Seoul)
 * @return [start, end) 형태의 반개구간 (start inclusive, end exclusive)
 */
fun LocalDate.toTimeRange(): TimeRange = TimeRange(this.startEpochMillis(), this.endEpochMillis())

data class TimeRange(val start: Long, val end: Long)
