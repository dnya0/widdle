package day.widdle.widdle.support

import java.time.LocalDate
import java.time.ZoneId

private val zone = ZoneId.of("Asia/Seoul")
fun getToday() = LocalDate.now(zone)