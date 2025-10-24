package day.widdle.widdle.correction.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "correction")
data class CorrectionProperties(
    val openAi: OpenAi,
    val bareun: Bareun
)

data class OpenAi(
    val url: String,
    val organization: String,
    val project: String,
    val promptId: String,
    val key: String,
)

data class Bareun(
    val url: String,
    val key: String,
) {
}

