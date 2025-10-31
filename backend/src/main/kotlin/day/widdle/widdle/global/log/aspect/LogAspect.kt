package day.widdle.widdle.global.log.aspect

import day.widdle.widdle.global.support.logger
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAspect {

    private val log = logger()

    @Pointcut("@within(day.widdle.widdle.global.annotation.LogExternal)")
    fun classLoggingTarget() {
    }

    @Pointcut("@annotation(day.widdle.widdle.global.annotation.LogExternal)")
    fun methodLoggingTarget() {
    }

    @Around("classLoggingTarget(), methodLoggingTarget()")
    fun logExternalApiCall(joinPoint: ProceedingJoinPoint): Any? {
        val startTime = System.currentTimeMillis()
        val signature = joinPoint.signature.toShortString()
        val args = joinPoint.args.joinToString(", ")

        log.info("[OpenAPI Call] --> $signature | Request Args: [$args]")

        try {
            val result = joinPoint.proceed()
            val duration = System.currentTimeMillis() - startTime

            log.info(
                "[OpenAPI Call] <-- $signature | Duration: ${duration}ms | Response: [${
                    result.toString().take(100)
                }...]"
            )
            return result

        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime
            log.error("[OpenAPI Call] !-- $signature | Error: ${e.javaClass.simpleName}: ${e.message} | Duration: ${duration}ms")
            throw e
        }
    }

}
