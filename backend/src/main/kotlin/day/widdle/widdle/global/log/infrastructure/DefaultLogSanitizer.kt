package day.widdle.widdle.global.log.infrastructure

import day.widdle.widdle.global.log.support.LogSanitizer
import org.springframework.stereotype.Component

@Component
class DefaultLogSanitizer(
    private val maxLength: Int = 500,
    private val sensitiveKeys: Set<String> = setOf(
        "api-key", "secret-token", "client-id", "key"
    )
) : LogSanitizer {

    override fun sanitize(value: Any?): String = when (value) {
        null -> "null"
        is CharSequence -> sanitizeString(value.toString())
        else -> value.toString().take(maxLength)
    }

    private fun sanitizeString(input: String): String {
        var result = input
        sensitiveKeys.forEach { key ->
            val regex = Regex("(?i)($key=[^&]*)([&|#|\\s]|\$)")
            result = regex.replace(result) { match ->
                val boundary = match.groupValues.getOrNull(2) ?: ""
                "$key=******$boundary"
            }
        }
        return if (result.length > maxLength) result.take(maxLength) + "..." else result
    }

}
