package day.widdle.widdle.search.service.dto

import java.time.LocalDateTime

data class KoreanResponse(var channel: Channel)

data class Channel(
    var title: String?,
    var link: String?,
    var description: String?,
    var lastBuildDate: LocalDateTime,
    var total: Int?,
    var start: Int?,
    var num: Int?,
)