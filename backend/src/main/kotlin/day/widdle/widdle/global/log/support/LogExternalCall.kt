package day.widdle.widdle.global.log.support

inline fun <T> logExternalCall(
    signature: String,
    args: Array<Any?>,
    logger: org.slf4j.Logger,
    crossinline sanitize: (Any?) -> String,
    block: () -> T
): T {
    val startTime = System.currentTimeMillis()
    val sanitizedArgs = args.joinToString(", ") { sanitize(it) }

    logger.info("[OpenAPI Call] --> $signature | Request Args: [$sanitizedArgs]")

    return try {
        val result = block()
        val duration = System.currentTimeMillis() - startTime

        logger.info(
            "[OpenAPI Call] <-- $signature | Duration: ${duration}ms | Response: [${sanitize(result)}...]"
        )
        result
    } catch (e: Exception) {
        val duration = System.currentTimeMillis() - startTime
        logger.error(
            "[OpenAPI Call] !-- $signature | Error: ${e.javaClass.simpleName}: ${e.message} | Duration: ${duration}ms"
        )
        throw e
    }
}
