package day.widdle.widdle.global.event

data class WordSavedEvent(val word: String, val jamo: List<String>) : WiddleEvent
