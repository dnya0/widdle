package day.widdle.widdle.global.log.filter

import day.widdle.widdle.global.support.logger
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import java.util.UUID

@Order(Ordered.HIGHEST_PRECEDENCE)
class MDCLoggingFilter : Filter {
    private val requestId = "requestId"
    private val log = logger()

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        val startTime = System.currentTimeMillis()

        try {
            MDC.put(requestId, createUUID())

            val httpRequest = request as? HttpServletRequest ?: return
            val httpResponse = response as? HttpServletResponse ?: return

            log.info("--> ${httpRequest.method} ${httpRequest.requestURI}")
            chain?.doFilter(request, response)

            val duration = System.currentTimeMillis() - startTime
            log.info("<-- ${httpResponse.status} ${httpRequest.method} ${httpRequest.requestURI} (${duration}ms)")
        } finally {
            MDC.remove(requestId)
        }
    }

    private fun createUUID() = UUID.randomUUID().toString()

}
