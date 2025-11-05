package day.widdle.widdle.search.service.api

import day.widdle.widdle.global.annotation.LogExternal
import day.widdle.widdle.global.support.logger
import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.WordResponse
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@LogExternal
class EnglishSearchApi(
    private val clientProperties: ClientProperties,
    @param:Qualifier("getMethodWebClient") private val builder: WebClient.Builder,
) : SearchApi {
    private val log = logger()

    private val webClient = builder
        .baseUrl(clientProperties.en.requestUrl)
        .build()

    override suspend fun search(word: String): Boolean = runCatching {
        val response = sendRequestEn(word).awaitSingleOrNull()
        response?.any { it.word.equals(word, ignoreCase = true) } ?: false
    }.onFailure {
        log.error("Could not search word", it)
    }.getOrDefault(false)

    private fun sendRequestEn(word: String): Mono<List<WordResponse>?> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .pathSegment(word)
                .build()
        }
        .retrieve()
        .bodyToMono(object : ParameterizedTypeReference<List<WordResponse>>() {})

}
