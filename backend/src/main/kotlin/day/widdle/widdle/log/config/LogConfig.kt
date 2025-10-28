package day.widdle.widdle.log.config

import day.widdle.widdle.log.filter.MDCLoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered

@Configuration
class LogConfig {

    @Bean
    fun loggingFilterRegistration(filter: MDCLoggingFilter): FilterRegistrationBean<MDCLoggingFilter> {
        val registration = FilterRegistrationBean(filter)
        registration.order = Ordered.HIGHEST_PRECEDENCE
        registration.addUrlPatterns("/*")

        return registration
    }
}
