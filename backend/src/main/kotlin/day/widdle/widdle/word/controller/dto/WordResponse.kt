package day.widdle.widdle.word.controller.dto

import day.widdle.widdle.word.domain.Word

data class WordResponse(
    val id: String,
    val word: String,
    val jamo: List<String>,
    val isKorean: Boolean,
    val length: Int
)

fun Word.toResponseDto(): WordResponse = WordResponse(
    id = this.id.value,
    word = this.wordInfo.wordText,
    jamo = this.wordInfo.wordJamo,
    isKorean = this.wordInfo.isKorean,
    length = this.wordInfo.length
)
