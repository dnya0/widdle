package day.widdle.widdle.global.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


class UrlValidationFilter : Filter {
    private val allowedPatterns = listOf(
        "^/api$",
        "^/api/[a-zA-Z]+$",
        "^/api/[a-zA-Z0-9가-힣-]{1,50}$"
    ).map { it.toRegex() }

    override fun doFilter(
        p0: ServletRequest?,
        p1: ServletResponse?,
        p2: FilterChain?
    ) {
        val request = p0 as HttpServletRequest

        request.characterEncoding = "UTF-8"
        p1?.characterEncoding = "UTF-8"

        val url = request.requestURI
        val path = url.split('?')[0]

        if (url.contains("\\") || !allowedPatterns.any { it.matches(path) }) {
            val response = p1 as HttpServletResponse
            response.contentType = "text/plain;charset=UTF-8"
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "유효하지 않은 URL 형식입니다.")
            return
        }

        p2?.doFilter(p0, p1)
    }

}
