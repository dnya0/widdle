package day.widdle.widdle.search.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client")
data class ClientProperties(
    val kr: Kr,
    val en: En
)

data class Kr(
    val naver: Naver,
    val dictionary: Dictionary
)

data class En(
    val requestUrl: String = "",
    val key: String = ""
)

data class Dictionary(
    val requestUrl: String = "",
    val key: String = ""
)

data class Naver(
    val id: String = "",
    val secret: String = "",
    val requestUrl: String = ""
)
