package day.widdle.widdle.correction.service.checker

import day.widdle.widdle.correction.config.CorrectionProperties
import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.CorrectionResult
import day.widdle.widdle.correction.service.dto.bareun.CorrectErrorRequest
import day.widdle.widdle.correction.service.dto.bareun.CorrectErrorResponse
import day.widdle.widdle.global.annotation.LogExternal
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.logger
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.netty.http.client.HttpClient

@Service
@LogExternal
class BareunSpellChecker(
    private val correctionProperties: CorrectionProperties,
    @param:Qualifier("postMethodWebClient") private val builder: WebClient.Builder,
    private val env: Environment
) : KoreanSpellChecker {
    private val log = logger()

    private val webClient = builder
        .defaultHeader("api-key", correctionProperties.bareun.key)
        .baseUrl(correctionProperties.bareun.url)
        .let { isProfileDevThanSetSSLIgnore(it) }
        .build()

    override suspend fun correct(word: String, wordJamo: List<String>): CorrectionResult {
        val response = sendRequest(word)?.revised
        return CorrectionResult.of(word, response, wordJamo)
    }

    private suspend fun sendRequest(word: String): CorrectErrorResponse? = webClient.post()
        .bodyValue(CorrectErrorRequest.create(word))
        .retrieve()
        .onStatus({ status -> status.isError }) { handleErrorResponse(it) }
        .bodyToMono(CorrectErrorResponse::class.java)
        .doOnNext { log.info("🔍 Bareun API Raw Response:\n$it") }
        .onErrorMap {
            WiddleException(
                "Failed to retrieve or parse Bareun API response: ${it.message}", it
            )
        }
        .awaitSingleOrNull()

    private fun isProfileDevThanSetSSLIgnore(builder: WebClient.Builder): WebClient.Builder {
        val isDevProfile = env.acceptsProfiles(Profiles.of("dev"))

        return if (isDevProfile) {
            val sslContext = SslContextBuilder.forClient()
                .trustManager(InsecureTrustManagerFactory.INSTANCE)
                .build()

            val httpClient = HttpClient.create()
                .secure { t -> t.sslContext(sslContext) }

            builder.clientConnector(ReactorClientHttpConnector(httpClient))
        } else {
            builder
        }
    }

    private fun handleErrorResponse(clientResponse: ClientResponse): Mono<Throwable> =
        clientResponse.bodyToMono(String::class.java)
            .flatMap { errorBody ->
                val httpStatus = clientResponse.statusCode().value()
                Mono.error(
                    WiddleException(
                        "Bareun API returned HTTP $httpStatus error. Response Body: $errorBody"
                    )
                )
            }
}
