package day.widdle.widdle.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableAsync
import day.widdle.widdle.search.config.ClientProperties

@Configuration
@EnableAsync
@EnableConfigurationProperties(ClientProperties::class)
class AppConfig