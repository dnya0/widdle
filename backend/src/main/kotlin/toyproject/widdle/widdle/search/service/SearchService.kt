package toyproject.widdle.widdle.search.service

import org.springframework.context.ApplicationEventPublisher
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.search.config.ClientProperties
import toyproject.widdle.widdle.search.event.NewWordEvent
import toyproject.widdle.widdle.search.service.dto.NaverResponse
import toyproject.widdle.widdle.search.service.dto.WordResponse
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class SearchService(
    private val clientProperties: ClientProperties,
    private val publisher: ApplicationEventPublisher
) {
    private val log = logger()

    private val webClientKr = WebClient.builder()
        .baseUrl(clientProperties.kr.requestUrl)
        .defaultHeader("X-Naver-Client-Id", clientProperties.kr.naver.id)
        .defaultHeader("X-Naver-Client-Secret", clientProperties.kr.naver.secret)
        .build()

    private val webClientEn = WebClient.builder()
        .baseUrl(clientProperties.en.requestUrl)
        .build()

    fun hasWordInDictionary(word: String, wordJamo: List<String>): Boolean {
        val isKorean = word.isKorean()
        val flag = if (isKorean) {
            hasWordInNaverDictionary(word)
        } else {
            hasWordInEnglishDictionary(word)
        }

        if (flag) {
            publisher.publishEvent(NewWordEvent(word, wordJamo, isKorean))
        }
        return flag
    }

    private fun hasWordInNaverDictionary(word: String): Boolean {
        val response = sendRequestKr(word) ?: return false
        return response.rss.channel.total > 0
    }

    private fun hasWordInEnglishDictionary(word: String): Boolean {
        try {
            val response = sendRequestEn(word) ?: return false
            return response[0].word == word
        } catch (e: Exception) {
            log.error("exception: $e")
            return false
        }
    }

    private fun sendRequestKr(word: String): NaverResponse? = webClientKr.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam("query", encode(word))
                .build()
        }
        .retrieve()
        .bodyToMono(NaverResponse::class.java)
        .block()

    private fun sendRequestEn(word: String): List<WordResponse>? = webClientEn.get()
        .uri { uriBuilder ->
            uriBuilder
                .pathSegment(word)
                .build()
        }
        .retrieve()
        .bodyToMono(object : ParameterizedTypeReference<List<WordResponse>>() {})
        .block()

    private fun encode(word: String) = runCatching { URLEncoder.encode(word, StandardCharsets.UTF_8) }

    private fun String.isKorean(): Boolean = this.toCharArray().all { char -> char.code in 0xAC00..0xD7A3 }

}