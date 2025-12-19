package day.widdle.widdle.word.service.dto

data class WordSaveDto(
    val word: String,
    val jamo: List<String>? = null,
    val isKorean: Boolean,
)
