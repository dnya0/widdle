package toyproject.widdle.widdle.search.event

data class NewWordEvent(val word: String, val jamo: List<String>, val isKorean: Boolean)