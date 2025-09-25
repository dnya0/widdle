package day.widdle.widdle.search.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "client")
data class ClientProperties(
    var kr: Kr,
    val en: En
)

data class Kr(
    var naver: Naver,
    var dictionary: Dictionary
)

data class En(
    var requestUrl: String = ""
)

data class Dictionary(
    var requestUrl: String = ""
)

data class Naver(
    var id: String = "",
    var secret: String = "",
    var requestUrl: String = ""
)
