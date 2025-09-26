package day.widdle.widdle.search.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.http.codec.xml.Jaxb2XmlEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun webClientBuilder(): WebClient.Builder {
        return WebClient.builder()
            .codecs { configurer ->
                configurer.customCodecs().register(Jaxb2XmlDecoder())
                configurer.customCodecs().register(Jaxb2XmlEncoder())
            }
    }
}