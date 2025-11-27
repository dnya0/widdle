package day.widdle.widdle.search.service.api

import day.widdle.widdle.global.annotation.LogExternal
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.logger
import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.KunsasResponse
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
@LogExternal
class KoreanSearchApi(
    private val clientProperties: ClientProperties,
    @param:Qualifier("getMethodWebClientWithTextJson") private val builder: WebClient.Builder,
) : SearchApi {
    private val log = logger()
    private val keyParameterName = "key";

    private val webClient = builder
        .baseUrl(clientProperties.kr.dictionary.requestUrl)
        .build()

    override suspend fun search(word: String): Boolean = withContext(MDCContext()) {
        runCatching {
            val response = sendRequest(word).awaitSingleOrNull()
            val channel = response?.channel
            if (channel?.total != 0) {
                channel?.item?.any { it.word == word.replace("-", "") } ?: false
                return@runCatching true
            }
            return@runCatching false
        }.onFailure {
            log.error("Could not search word", it)
        }.getOrDefault(false)
    }

    private fun sendRequest(word: String): Mono<KunsasResponse> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .queryParam(keyParameterName, clientProperties.kr.dictionary.key)
                .queryParam("q", word)
                .queryParam("part", PART_WORD)
                .queryParam("req_type", "json")
                .build()
        }
        .retrieve()
        .bodyToMono(KunsasResponse::class.java)
        .doOnNext { log.info("🔍 Kunsas API Raw Response:\n$it") }
        .onErrorMap { throwable ->
            WiddleException("Failed to retrieve or parse Korean API response: ${throwable.message}", throwable)
        }

    companion object {
        private const val PART_WORD = "word"
    }
}
