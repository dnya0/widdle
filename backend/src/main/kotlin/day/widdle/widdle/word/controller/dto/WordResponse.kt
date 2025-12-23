package day.widdle.widdle.word.controller.dto

import day.widdle.widdle.word.domain.Word
import day.widdle.widdle.word.service.dto.DailyWordDto

data class WordResponse(
    val id: String,
    val word: String,
    val jamo: List<String>,
    val isKorean: Boolean
)

fun Word.toDto(): DailyWordDto = DailyWordDto(
    id = this.id.value,
    word = this.wordInfo.wordText,
    jamo = this.wordInfo.wordJamo,
    isKorean = this.wordInfo.isKorean
)
