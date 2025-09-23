package toyproject.widdle.widdle.search.service.api

import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import toyproject.widdle.widdle.logger.logger
import toyproject.widdle.widdle.search.config.ClientProperties
import toyproject.widdle.widdle.search.service.SearchApi
import toyproject.widdle.widdle.search.service.dto.WordResponse

@Service
class EnglishSearchApi(
    private val clientProperties: ClientProperties,
) : SearchApi {
    private val log = logger()

    private val webClient = WebClient.builder()
        .baseUrl(clientProperties.en.requestUrl)
        .build()

    override fun search(word: String): Boolean {
        try {
            val response = sendRequestEn(word) ?: return false
            return response[0].word == word
        } catch (e: Exception) {
            log.error("exception: $e")
            return false
        }
    }

    private fun sendRequestEn(word: String): List<WordResponse>? = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .pathSegment(word)
                .build()
        }
        .retrieve()
        .bodyToMono(object : ParameterizedTypeReference<List<WordResponse>>() {})
        .block()

}