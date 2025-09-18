package toyproject.widdle.widdle.config

import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse


class UrlValidationFilter : Filter {

    override fun doFilter(
        p0: ServletRequest?,
        p1: ServletResponse?,
        p2: FilterChain?
    ) {
        val url = (p0 as HttpServletRequest).requestURI.toString()

        if (url.contains("\\") || !url.matches("[a-zA-Z0-9/_-]+".toRegex())) {
            (p1 as HttpServletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format")
            return
        }

        p2?.doFilter(p0, p1)
    }

}