package day.widdle.widdle.global.log.aspect

import day.widdle.widdle.global.log.support.LogSanitizer
import day.widdle.widdle.global.log.support.logExternalCall
import day.widdle.widdle.global.support.loggerDelegate
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.springframework.stereotype.Component

@Aspect
@Component
class LogAspect(private val sanitizer: LogSanitizer) {

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
    fun logExternalApiCall(joinPoint: ProceedingJoinPoint): Any? =
        logExternalCall(
            signature = joinPoint.signature.toShortString(),
            args = joinPoint.args,
            logger = log,
            sanitize = sanitizer::sanitize
        ) {
            joinPoint.proceed()
        }

}
