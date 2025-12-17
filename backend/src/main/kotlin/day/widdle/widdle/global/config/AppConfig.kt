package day.widdle.widdle.global.config

import day.widdle.widdle.correction.config.CorrectionProperties
import day.widdle.widdle.search.config.ClientProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.scheduling.annotation.EnableAsync

@Configuration
@EnableAsync
@EnableJpaAuditing
@EnableConfigurationProperties(ClientProperties::class, CorrectionProperties::class)
class AppConfig
