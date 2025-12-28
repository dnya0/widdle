package day.widdle.widdle.word.service.dto

import day.widdle.widdle.word.controller.dto.WordResponse

data class DailyWordDto(
    val id: String,
    val word: String,
    val jamo: List<String>,
    val isKorean: Boolean
)

fun DailyWordDto.toWordResponse(): WordResponse = WordResponse(
    id = this.id,
    word = this.word,
    jamo = this.jamo,
    isKorean = this.isKorean
)
