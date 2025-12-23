package day.widdle.widdle.word.controller.dto

import day.widdle.widdle.word.service.dto.WordSaveDto

data class WordSaveRequest(
    val word: String,
    val jamo: List<String>? = null,
    val isKorean: Boolean,
) {
    fun toDto(): WordSaveDto = WordSaveDto(
        word = word,
        jamo = jamo,
        isKorean = isKorean,
    )
}
