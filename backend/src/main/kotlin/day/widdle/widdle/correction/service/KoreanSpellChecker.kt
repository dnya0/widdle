package day.widdle.widdle.correction.service

import day.widdle.widdle.correction.service.dto.CorrectionResult

interface KoreanSpellChecker {

    fun correct(word: String): CorrectionResult

}