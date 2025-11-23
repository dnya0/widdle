package day.widdle.widdle.global.log.filter

import day.widdle.widdle.global.support.logger
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.MDC
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class MDCLoggingFilter : Filter {
    private val requestId = "requestId"
    private val log = logger()
    private val excludeURI = "/health"

    override fun doFilter(
        request: ServletRequest?,
        response: ServletResponse?,
        chain: FilterChain?
    ) {
        val httpRequest = request as? HttpServletRequest ?: return
        val httpResponse = response as? HttpServletResponse ?: return

        if (httpRequest.requestURI == excludeURI) {
            chain?.doFilter(request, response)
            return
        }

        val startTime = System.currentTimeMillis()

        try {
            MDC.put(requestId, "[request-${createUUID()}]")

            log.info("--> ${httpRequest.method} ${httpRequest.requestURI}")
            chain?.doFilter(request, response)

            val duration = System.currentTimeMillis() - startTime
            log.info("<-- ${httpResponse.status} ${httpRequest.method} ${httpRequest.requestURI} (${duration}ms)")
        } finally {
            MDC.remove(requestId)
        }
    }

    private fun createUUID() = UUID.randomUUID().toString().replace("-", "")

}
