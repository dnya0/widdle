package toyproject.widdle.widdle.word.controller.dto

import toyproject.widdle.widdle.word.domain.Word

data class WordResponse(
    val id: String,
    val word: String,
    val jamo: List<String>,
    val isKorean: Boolean,
    val length: Int
)

fun Word.toResponseDto(): WordResponse = WordResponse(
    id = id,
    word = wordText,
    jamo = wordJamo,
    isKorean = isKorean,
    length = length
)
