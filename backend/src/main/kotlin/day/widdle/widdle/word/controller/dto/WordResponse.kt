package day.widdle.widdle.word.controller.dto

data class WordResponse(
    val id: String,
    val word: String,
    val jamo: List<String>,
    val isKorean: Boolean
)
