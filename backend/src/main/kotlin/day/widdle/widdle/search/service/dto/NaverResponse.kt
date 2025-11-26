package day.widdle.widdle.search.service.dto

import java.time.LocalDateTime

data class NaverResponse(val rss: Rss)

data class Rss(val channel: Channel)

data class Channel(
    val lastBuildDate: LocalDateTime,
    val total: Int,
    val start: Int,
    val display: Int,
    val item: List<Item>?
)

data class Item(
    val title: String,
    val link: String,
    val description: String,
    val thumbnail: String
)
