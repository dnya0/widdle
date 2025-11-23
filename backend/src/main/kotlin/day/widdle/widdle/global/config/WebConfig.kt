package day.widdle.widdle.global.config

import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    fun urlValidationFilter(): FilterRegistrationBean<UrlValidationFilter?> {
        val registrationBean = FilterRegistrationBean<UrlValidationFilter?>()
        registrationBean.filter = UrlValidationFilter()
        registrationBean.addUrlPatterns("/api/*")
        return registrationBean
    }
}
