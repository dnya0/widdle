package day.widdle.widdle.global.log.aspect

import day.widdle.widdle.global.support.loggerDelegate
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAspect {

    private val log by loggerDelegate()

    @Suppress("EmptyFunctionBlock")
    @Pointcut("@within(day.widdle.widdle.global.annotation.LogExternal)")
    fun classLoggingTarget() {
    }

    @Suppress("EmptyFunctionBlock")
    @Pointcut("@annotation(day.widdle.widdle.global.annotation.LogExternal)")
    fun methodLoggingTarget() {
    }

    @Around("classLoggingTarget() || methodLoggingTarget()")
    fun logExternalApiCall(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val signature = joinPoint.signature.toShortString()
        val args = joinPoint.args.joinToString(", ") { sanitizeForLogging(it) }

        log.info("[OpenAPI Call] --> $signature | Request Args: [${args}]")

        try {
            val result = joinPoint.proceed()
            val duration = System.currentTimeMillis() - startTime

            log.info("[OpenAPI Call] <-- $signature | Duration: ${duration}ms | Response: [${sanitizeForLogging(result)}...]")
            return result
        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            log.error("[OpenAPI Call] !-- $signature | Error: ${e.javaClass.simpleName}: ${e.message} | Duration: ${duration}ms")
            throw e
        }
    }

    private fun sanitizeForLogging(value: Any?): String = when (value) {
        null -> "null"
        is CharSequence -> {
            val str = if (value.contains("?")) maskSensitiveQueryParams(value.toString()) else value.toString()
            if (str.length > 500) str.take(500) + "..." else str
        }

        else -> value.toString().take(500)
    }

    private fun maskSensitiveQueryParams(queryString: String): String {
        val sensitiveKeys = setOf("api-key", "secret-token", "client-id", "key")
        var maskedString = queryString

        sensitiveKeys.forEach { key ->
            val regex = Regex("(?i)($key=[^&]*)([&|#|\\s]|\$)")

            maskedString = regex.replace(maskedString) { matchResult ->
                val boundary = matchResult.groupValues.getOrNull(2) ?: ""
                "$key=******$boundary"
            }
        }
        return maskedString
    }

}
