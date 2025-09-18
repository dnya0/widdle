package toyproject.widdle.widdle.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/api/**")
            .allowedOrigins("https://www.widdle.day", "http://localhost:3000")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowCredentials(true)
    }

    @Bean
    fun urlValidationFilter(): FilterRegistrationBean<UrlValidationFilter?> {
        val registrationBean = FilterRegistrationBean<UrlValidationFilter?>()
        registrationBean.filter = UrlValidationFilter()
        registrationBean.addUrlPatterns("/api/*")
        return registrationBean
    }
}