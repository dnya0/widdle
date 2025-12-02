package day.widdle.widdle.search.service.api

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import day.widdle.widdle.global.annotation.LogExternal
import day.widdle.widdle.global.exception.WiddleException
import day.widdle.widdle.global.support.loggerDelegate
import day.widdle.widdle.search.config.ClientProperties
import day.widdle.widdle.search.service.SearchApi
import day.widdle.widdle.search.service.dto.DictionaryEntry
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.slf4j.MDCContext
import kotlinx.coroutines.withContext
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
    private val objectMapper = ObjectMapper().registerModule(
        KotlinModule.Builder()
            .withReflectionCacheSize(512)
            .configure(KotlinFeature.NullToEmptyCollection, false)
            .configure(KotlinFeature.NullToEmptyMap, false)
            .configure(KotlinFeature.NullIsSameAsDefault, false)
            .configure(KotlinFeature.SingletonSupport, false)
            .configure(KotlinFeature.StrictNullChecks, false)
            .build()
    )

    private val log by loggerDelegate()

    private val webClient = builder
        .baseUrl(clientProperties.en.requestUrl)
        .build()

    override suspend fun search(word: String): Boolean = withContext(MDCContext()) {
        runCatching {
            val response = sendRequestEn(word).awaitSingleOrNull()
            if (response.isNullOrEmpty()) {
                return@runCatching false
            }

            val firstElement = response.first()
            if (firstElement is String) {
                return@runCatching false
            }

            objectMapper.convertValue(
                response,
                object : TypeReference<List<DictionaryEntry>>() {}
            ).any { it.meta.id.substringBefore(":").equals(word, ignoreCase = true) }
        }.onFailure {
            log.error("Could not search word", it)
        }.getOrDefault(false)
    }

    private fun sendRequestEn(word: String): Mono<List<Any>?> = webClient.get()
        .uri { uriBuilder ->
            uriBuilder
                .pathSegment(word)
                .queryParam("key", clientProperties.en.key)
                .build()
        }
        .retrieve()
        .bodyToMono(object : ParameterizedTypeReference<List<Any>>() {})
        .onErrorMap { throwable ->
            WiddleException("Failed to retrieve or parse Merriam API response: ${throwable.message}", throwable)
        }

}

