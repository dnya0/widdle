package toyproject.widdle.widdle.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration
import toyproject.widdle.widdle.search.config.ClientProperties

@Configuration
@EnableConfigurationProperties(ClientProperties::class)
class AppConfig