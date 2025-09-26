package day.widdle.widdle.search.service.api

import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.NaverResponse
import day.widdle.widdle.support.encode
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class NaverSearchApi(
    private val clientProperties: ClientProperties,
) : SearchApi {

    private val webClient = WebClient.builder()
        .baseUrl(clientProperties.kr.naver.requestUrl)
        .defaultHeader("X-Naver-Client-Id", clientProperties.kr.naver.id)
        .defaultHeader("X-Naver-Client-Secret", clientProperties.kr.naver.secret)
        .build()

    override suspend fun search(word: String): Boolean {
        val response = sendRequest(word).awaitSingleOrNull() ?: return false
        return response.rss.channel.total > 0
    }

    private fun sendRequest(word: String): Mono<NaverResponse?> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam("query", encode(word))
                .build()
        }
        .retrieve()
        .bodyToMono(NaverResponse::class.java)
}