package day.widdle.widdle.correction.service

import day.widdle.widdle.correction.service.dto.CorrectionResult

interface KoreanSpellChecker {

    suspend fun correct(word: String, wordJamo: List<String>): CorrectionResult

}
