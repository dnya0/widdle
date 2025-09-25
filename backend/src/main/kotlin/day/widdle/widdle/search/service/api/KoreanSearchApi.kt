package day.widdle.widdle.search.service.api

import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.KoreanResponse
import day.widdle.widdle.support.encode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class KoreanSearchApi(
    private val clientProperties: ClientProperties,
) : SearchApi {
    private val webClient = WebClient.builder()
        .baseUrl(clientProperties.kr.dictionary.requestUrl)
        .build()

    override fun search(word: String): Boolean {
        val response = sendRequest(word) ?: return false
        return response.channel.total > 0
    }

    private fun sendRequest(word: String): KoreanResponse? = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam("q", encode(word))
                .build()
        }
        .retrieve()
        .bodyToMono(KoreanResponse::class.java)
        .block()

}