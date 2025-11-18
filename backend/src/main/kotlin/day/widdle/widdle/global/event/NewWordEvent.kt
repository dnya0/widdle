package day.widdle.widdle.global.event

import day.widdle.widdle.global.support.isKorean
import day.widdle.widdle.global.support.toJamoList

data class NewWordEvent(val word: String, val jamo: List<String>, val isKorean: Boolean) : WiddleEvent {
    companion object {
        fun to(word: String, jamo: List<String> = word.toJamoList()) =
            NewWordEvent(word, jamo, word.isKorean())
    }
}
