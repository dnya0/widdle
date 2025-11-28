package day.widdle.widdle.search.service.api

import day.widdle.widdle.global.annotation.LogExternal
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.NaverResponse
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@LogExternal
class NaverSearchApi(
    private val clientProperties: ClientProperties,
    @param:Qualifier("getMethodWebClient") private val builder: WebClient.Builder
) : SearchApi {

    private val log by loggerDelegate()

    private val webClient = builder
        .baseUrl(clientProperties.kr.naver.requestUrl)
        .defaultHeader("X-Naver-Client-Id", clientProperties.kr.naver.id)
        .defaultHeader("X-Naver-Client-Secret", clientProperties.kr.naver.secret)
        .build()

    override suspend fun search(word: String): Boolean = runCatching {
        val response = sendRequest(word).awaitSingleOrNull() ?: return false
        response.rss.channel.total > 0
    }.onFailure { e ->
        log.error("NaverSearchApi.search failed for word={}", word, e)
    }.getOrDefault(false)

    private fun sendRequest(word: String): Mono<NaverResponse?> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam("query", encode(word))
                .build()
        }
        .retrieve()
        .bodyToMono(NaverResponse::class.java)
}
