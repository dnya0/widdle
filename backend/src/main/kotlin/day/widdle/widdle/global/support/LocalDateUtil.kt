package day.widdle.widdle.global.support

import java.time.LocalDate
import java.time.ZoneId
import kotlin.time.Clock

private val zone = ZoneId.of("Asia/Seoul")
fun getToday(): LocalDate = LocalDate.now(zone)

/**
 * 현재 시각을 Unix Timestamp(ms)로 반환한다.
 *
 * - 기준: UTC
 * - 용도: DB 저장용 기준 시간
 */
fun nowEpochMillis(): Long = Clock.System.now().toEpochMilliseconds()

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
 * 해당 날짜의 시작 시각과 다음 날 시작 시각을 Pair로 반환한다.
 *
 * - 용도: 특정 날짜 범위 내의 데이터를 조회하기 위한 [start, end) 구간 생성
 * - 기준 타임존: zone (예: Asia/Seoul)
 * - 반환 값: TimeRange(오늘 00:00:00 UTC ms, 내일 00:00:00 UTC ms)
 */
fun LocalDate.toTimeRange(): TimeRange = TimeRange(this.startEpochMillis(), this.endEpochMillis())

data class TimeRange(val start: Long, val end: Long)
