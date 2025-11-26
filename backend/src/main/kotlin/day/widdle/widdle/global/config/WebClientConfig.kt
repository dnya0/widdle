package day.widdle.widdle.global.config

import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.http.codec.xml.Jaxb2XmlEncoder
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig {

    private fun clientConnector() = ReactorClientHttpConnector(
        HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3_000)
            .responseTimeout(Duration.ofSeconds(5))
            .doOnConnected
            { c ->
                c.addHandlerLast(ReadTimeoutHandler(5))
                    .addHandlerLast(WriteTimeoutHandler(5))
            }
    )

    @Bean
    fun postMethodWebClient(): WebClient.Builder = WebClient.builder()
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(clientConnector())

    @Bean
    fun xmlWebClient(): WebClient.Builder = WebClient.builder()
        .clientConnector(clientConnector())
        .exchangeStrategies(ExchangeStrategies.builder().codecs { configurer ->
            configurer.defaultCodecs().maxInMemorySize(1024 * 1024)
            configurer.defaultCodecs().jaxb2Decoder(Jaxb2XmlDecoder())
            configurer.defaultCodecs().jaxb2Encoder(Jaxb2XmlEncoder())
        }.build())

    @Bean
    fun getMethodWebClient(): WebClient.Builder = WebClient.builder()
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(clientConnector())
}
