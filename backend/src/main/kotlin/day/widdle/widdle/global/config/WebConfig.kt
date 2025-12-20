package day.widdle.widdle.global.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig(
    @param:Value("\${cors.allowed-origins}") private val allowedOrigins: Array<String>
) : WebMvcConfigurer {

    @Bean
    fun urlValidationFilter(): FilterRegistrationBean<UrlValidationFilter?> =
        FilterRegistrationBean(UrlValidationFilter()).apply {
            addUrlPatterns("/api/*")
            order = 1
        }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins(*allowedOrigins)
            .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
            .allowCredentials(true)
            .maxAge(3600)
    }
}
