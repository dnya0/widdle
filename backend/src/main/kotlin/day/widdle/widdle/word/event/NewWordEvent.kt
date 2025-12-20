package day.widdle.widdle.word.event

import day.widdle.widdle.global.event.WiddleEvent
import day.widdle.widdle.global.support.containsHangul
import day.widdle.widdle.global.support.toJamoList

data class NewWordEvent(val word: String, val jamo: List<String>, val isKorean: Boolean) : WiddleEvent {
    companion object {
        fun to(word: String, jamo: List<String> = word.toJamoList()) =
            NewWordEvent(word, jamo, word.containsHangul())
    }
}
