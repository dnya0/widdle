package toyproject.widdle.widdle.search.service.api

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import toyproject.widdle.widdle.search.config.ClientProperties
import toyproject.widdle.widdle.search.service.SearchApi
import toyproject.widdle.widdle.search.service.dto.NaverResponse
import toyproject.widdle.widdle.support.encode

@Service
class NaverSearchApi(
    private val clientProperties: ClientProperties,
) : SearchApi {

    private val webClient = WebClient.builder()
        .baseUrl(clientProperties.kr.requestUrl)
        .defaultHeader("X-Naver-Client-Id", clientProperties.kr.naver.id)
        .defaultHeader("X-Naver-Client-Secret", clientProperties.kr.naver.secret)
        .build()

    override fun search(word: String): Boolean {
        val response = sendRequest(word) ?: return false
        return response.rss.channel.total > 0
    }

    private fun sendRequest(word: String): NaverResponse? = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam("query", encode(word))
                .build()
        }
        .retrieve()
        .bodyToMono(NaverResponse::class.java)
        .block()
}