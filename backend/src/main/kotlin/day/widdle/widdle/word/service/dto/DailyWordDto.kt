package day.widdle.widdle.word.service.dto

import day.widdle.widdle.word.controller.dto.WordResponse
import day.widdle.widdle.word.domain.Word

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

fun Word.toDto(): DailyWordDto = DailyWordDto(
    id = this.id.value,
    word = this.wordInfo.wordText,
    jamo = this.wordInfo.wordJamo,
    isKorean = this.wordInfo.isKorean
)
