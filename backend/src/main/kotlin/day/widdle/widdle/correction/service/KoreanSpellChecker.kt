package day.widdle.widdle.correction.service

import day.widdle.widdle.correction.service.dto.bareun.CorrectionResult

interface KoreanSpellChecker {

    suspend fun correct(word: String): CorrectionResult

}