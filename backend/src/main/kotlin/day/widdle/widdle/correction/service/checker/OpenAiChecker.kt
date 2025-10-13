package day.widdle.widdle.correction.service.checker

import day.widdle.widdle.correction.config.CorrectionProperties
import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.bareun.CorrectionResult
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

    override suspend fun correct(word: String): CorrectionResult {
        TODO("Not yet implemented")
    }

}