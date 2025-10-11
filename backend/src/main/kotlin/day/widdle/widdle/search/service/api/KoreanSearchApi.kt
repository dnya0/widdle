package day.widdle.widdle.search.service.api

import day.widdle.widdle.exception.WiddleException
import day.widdle.widdle.logger.logger
import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.KoreanResponse
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class KoreanSearchApi(
    private val clientProperties: ClientProperties,
    @param:Qualifier("xmlWebClient") private val builder: WebClient.Builder,
) : SearchApi {
    private val log = logger()

    private val webClient = builder
        .baseUrl(clientProperties.kr.dictionary.requestUrl)
        .build()

    override suspend fun search(word: String): Boolean = runCatching {
        val response = sendRequest(word).awaitSingleOrNull()
        response?.item?.any { it.word == word } ?: false
    }.onFailure {
        log.error("Could not search word", it)
    }.getOrDefault(false)

    private fun sendRequest(word: String): Mono<KoreanResponse> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam("key", clientProperties.kr.dictionary.key)
                .queryParam("q", word)
                .queryParam("part", "word")
                .build()
        }
        .accept(MediaType.APPLICATION_XML)
        .retrieve()
        .bodyToMono(KoreanResponse::class.java)
        .onErrorMap { throwable ->
            WiddleException("Failed to retrieve or parse Korean API response: ${throwable.message}", throwable)
        }

}