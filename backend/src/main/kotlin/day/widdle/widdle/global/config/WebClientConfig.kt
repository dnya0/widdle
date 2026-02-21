package day.widdle.widdle.global.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.netty.channel.ChannelOption
import io.netty.handler.ssl.SslContext
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.http.codec.xml.Jaxb2XmlDecoder
import org.springframework.http.codec.xml.Jaxb2XmlEncoder
import org.springframework.util.MimeType
import org.springframework.web.reactive.function.client.ExchangeStrategies
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig(
    private val objectMapper: ObjectMapper,
    private val sslContext: SslContext,
) {

    private fun clientConnector() = ReactorClientHttpConnector(
        HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3_000)
            .responseTimeout(Duration.ofSeconds(5))
            .doOnConnected { c ->
                c.addHandlerLast(ReadTimeoutHandler(5))
                    .addHandlerLast(WriteTimeoutHandler(5))
            }
            .secure { it.sslContext(sslContext) }
    )

    @Bean
    fun baseWebClientBuilder(): WebClient.Builder =
        WebClient.builder()
            .clientConnector(clientConnector())
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)

    @Bean
    fun postMethodWebClient(baseWebClientBuilder: WebClient.Builder): WebClient.Builder =
        baseWebClientBuilder.clone()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

    @Bean
    fun xmlWebClient(baseWebClientBuilder: WebClient.Builder): WebClient.Builder =
        baseWebClientBuilder.clone()
            .exchangeStrategies(
                ExchangeStrategies.builder().codecs { configurer ->
                    configurer.defaultCodecs().maxInMemorySize(1024 * 1024)
                    configurer.defaultCodecs().jaxb2Decoder(Jaxb2XmlDecoder())
                    configurer.defaultCodecs().jaxb2Encoder(Jaxb2XmlEncoder())
                }.build()
            )

    @Bean
    fun getMethodWebClient(baseWebClientBuilder: WebClient.Builder): WebClient.Builder =
        baseWebClientBuilder.clone()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)

    @Bean
    fun getMethodWebClientWithTextJson(baseWebClientBuilder: WebClient.Builder): WebClient.Builder {
        val mimeType = MimeType("text", "json")

        return baseWebClientBuilder.clone()
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .exchangeStrategies(
                ExchangeStrategies.builder().codecs { config ->
                    config.defaultCodecs().maxInMemorySize(16 * 1024 * 1024)
                    config.defaultCodecs().jackson2JsonDecoder(
                        Jackson2JsonDecoder(objectMapper, mimeType)
                    )
                    config.defaultCodecs().jackson2JsonEncoder(
                        Jackson2JsonEncoder(objectMapper, mimeType)
                    )
                }.build()
            )
    }
}
