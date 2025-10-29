package day.widdle.widdle.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.http.codec.xml.Jaxb2XmlEncoder
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class WebClientConfig {

    @Bean
    fun postMethodWebClient(): WebClient.Builder = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

    @Bean
    fun xmlWebClient(): WebClient.Builder = WebClient.builder()
        .codecs { configurer ->
            configurer.customCodecs().register(Jaxb2XmlDecoder())
            configurer.customCodecs().register(Jaxb2XmlEncoder())
        }

    @Bean
    fun getMethodWebClient(): WebClient.Builder = WebClient.builder()
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
}
