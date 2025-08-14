package toyproject.widdle.widdle.controller.dto

data class WordSaveRequest(
    val word: String,
    val jamo: List<String>? = null,
    val isKorean: Boolean,
)