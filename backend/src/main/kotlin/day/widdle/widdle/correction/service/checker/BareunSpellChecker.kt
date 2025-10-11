package day.widdle.widdle.correction.service.checker

import day.widdle.widdle.correction.config.CorrectionProperties
import day.widdle.widdle.correction.service.KoreanSpellChecker
import day.widdle.widdle.correction.service.dto.CorrectionResult
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class BareunSpellChecker(
    private val correctionProperties: CorrectionProperties,
    @param:Qualifier("getMethodWebClient") private val builder: WebClient.Builder,
) : KoreanSpellChecker {

    private val webClient = builder
        .baseUrl("https://${correctionProperties.bareun.url}:{${correctionProperties.bareun.port}}")
        .build()

    override fun correct(word: String): CorrectionResult {
        //todo 구현할 것
        return CorrectionResult(true, word)
    }

}