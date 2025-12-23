package day.widdle.widdle.word.event

import day.widdle.widdle.global.event.WiddleEvent

data class WordSavedEvent(val word: String, val jamo: List<String>) : WiddleEvent
