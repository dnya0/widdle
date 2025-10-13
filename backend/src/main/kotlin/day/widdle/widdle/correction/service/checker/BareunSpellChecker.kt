package day.widdle.widdle.correction.service.checker

import day.widdle.widdle.correction.config.CorrectionProperties
import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.bareun.CorrectErrorRequest
import day.widdle.widdle.correction.service.dto.bareun.CorrectErrorResponse
import day.widdle.widdle.correction.service.dto.bareun.CorrectionResult
import day.widdle.widdle.exception.WiddleException
import day.widdle.widdle.logger.logger
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class BareunSpellChecker(
    private val correctionProperties: CorrectionProperties,
    @param:Qualifier("postMethodWebClient") private val builder: WebClient.Builder,
) : KoreanSpellChecker {
    private val log = logger()

    private val webClient = builder
        .defaultHeader("api-key", correctionProperties.bareun.key)
        .baseUrl(correctionProperties.bareun.url)
        .build()

    override suspend fun correct(word: String): CorrectionResult {
        val response = sendRequest(word).awaitSingleOrNull()?.revised
        return CorrectionResult(response?.equals(word) ?: false, response ?: word)
    }

    private suspend fun sendRequest(word: String): Mono<CorrectErrorResponse> = webClient.post()
        .bodyValue(CorrectErrorRequest.create(word))
        .retrieve()
        .bodyToMono(CorrectErrorResponse::class.java)
        .onErrorMap { throwable ->
            WiddleException("Failed to retrieve or parse Bareun API response: ${throwable.message}", throwable)
        }

}