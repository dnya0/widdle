package day.widdle.widdle.log.filter

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.util.UUID

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter : Filter {
    private val requestId = "requestId"

    override fun doFilter(
        p0: ServletRequest?,
        p1: ServletResponse?,
        p2: FilterChain?
    ) {
        try {
            MDC.put(requestId, createUUID())
            p2?.doFilter(p0, p1)
        } finally {
            MDC.remove(requestId)
        }
    }

    private fun createUUID() = UUID.randomUUID().toString()

}
