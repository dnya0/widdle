package day.widdle.widdle.correction.service.dto

import day.widdle.widdle.correction.service.dto.value.CorrectionStatus

data class CorrectionResult(
    val isCorrect: Boolean,
    val correctWord: String?,
    val correctionStatus: CorrectionStatus
) {
    companion object {
        fun of(word: String, response: String?): CorrectionResult {
            val (correctionStatus, correctWord) = determineCorrectionResult(word, response)

            return CorrectionResult(
                isCorrect = word == response,
                correctWord = correctWord,
                correctionStatus = correctionStatus
            )
        }

        fun error() = CorrectionResult(false, null, CorrectionStatus.API_FAILURE)

        fun determineCorrectionResult(word: String, response: String?): DetermineCorrectionResult {
            return if (response.isNullOrBlank()) {
                DetermineCorrectionResult(CorrectionStatus.API_FAILURE, null)
            } else if (word == response) {
                DetermineCorrectionResult(CorrectionStatus.CORRECT, word)
            } else {
                DetermineCorrectionResult(CorrectionStatus.CORRECTED, response)
            }
        }
    }

    data class DetermineCorrectionResult(
        val correctionStatus: CorrectionStatus,
        val correctWord: String?
    )
}
