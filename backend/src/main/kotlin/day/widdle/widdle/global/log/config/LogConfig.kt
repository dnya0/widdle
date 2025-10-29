package day.widdle.widdle.global.log.config

import day.widdle.widdle.global.log.filter.MDCLoggingFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.core.Ordered

@Configuration
@EnableAspectJAutoProxy
class LogConfig {

    @Bean
    fun loggingFilterRegistration(filter: MDCLoggingFilter): FilterRegistrationBean<MDCLoggingFilter> {
        val registration = FilterRegistrationBean(filter)
        registration.order = Ordered.HIGHEST_PRECEDENCE
        registration.addUrlPatterns("/*")

        return registration
    }
}
