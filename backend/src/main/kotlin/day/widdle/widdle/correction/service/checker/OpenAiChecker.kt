package day.widdle.widdle.correction.service.checker

import day.widdle.widdle.correction.config.CorrectionProperties
import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.CorrectionResult
import day.widdle.widdle.correction.service.dto.openai.OpenAiRequest
import day.widdle.widdle.correction.service.dto.openai.OpenAiResponse
import day.widdle.widdle.exception.WiddleException
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class OpenAiChecker(
    private val correctionProperties: CorrectionProperties,
    @param:Qualifier("postMethodWebClient") private val builder: WebClient.Builder,
) : KoreanSpellChecker {

    private val webClient = builder
        .baseUrl(correctionProperties.openAi.url)
        .defaultHeader("OpenAI-Organization", correctionProperties.openAi.organization)
        .defaultHeader("OpenAI-Project", correctionProperties.openAi.project)
        .defaultHeaders { it.setBearerAuth(correctionProperties.openAi.key) }
        .build()

    override suspend fun correct(word: String): CorrectionResult = runCatching {
        val req = OpenAiRequest.of(correctionProperties.openAi.promptId, word)
        val response = sendRequest(req)?.output?.firstOrNull()?.content?.text
        CorrectionResult.of(word, response)
    }.getOrElse { CorrectionResult.error() }

    private suspend fun sendRequest(req: OpenAiRequest): OpenAiResponse? = webClient.post()
        .bodyValue(req)
        .retrieve()
        .bodyToMono(OpenAiResponse::class.java)
        .onErrorMap { throwable ->
            WiddleException("Failed to retrieve or parse openAi API response: ${throwable.message}", throwable)
        }
        .awaitSingleOrNull()

}
