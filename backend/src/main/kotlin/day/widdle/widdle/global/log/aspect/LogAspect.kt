package day.widdle.widdle.global.log.aspect

import day.widdle.widdle.global.support.logger
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

}
