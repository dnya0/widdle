package day.widdle.widdle.search.service.api

import day.widdle.widdle.logger.logger
import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.WordResponse
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class EnglishSearchApi(
    private val clientProperties: ClientProperties,
) : SearchApi {
    private val log = logger()

    private val webClient = WebClient.builder()
        .baseUrl(clientProperties.en.requestUrl)
        .build()

    override suspend fun search(word: String): Boolean {
        try {
            val response = sendRequestEn(word).awaitSingleOrNull() ?: return false
            return response[0].word == word
        } catch (e: Exception) {
            log.error("exception: $e")
            return false
        }
    }

    private fun sendRequestEn(word: String): Mono<List<WordResponse>?> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .pathSegment(word)
                .build()
        }
        .retrieve()
        .bodyToMono(object : ParameterizedTypeReference<List<WordResponse>>() {})

}