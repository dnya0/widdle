package day.widdle.widdle.global.support

import java.time.LocalDate
import java.time.ZoneId

private val zone = ZoneId.of("Asia/Seoul")
fun getToday(): LocalDate = LocalDate.now(zone)
