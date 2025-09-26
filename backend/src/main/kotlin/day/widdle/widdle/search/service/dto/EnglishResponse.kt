package day.widdle.widdle.search.service.dto

data class WordResponseList(
    val words: List<WordResponse>
)

data class WordResponse(
    val word: String,
    val phonetic: String? = null,
    val phonetics: List<Phonetic>,
    val origin: String? = null,
    val meaning: Meaning? = null
)

data class Phonetic(
    val text: String? = null,
    val audio: String? = null
)

data class Meaning(
    val exclamation: List<Definition> = emptyList(),
    val noun: List<Definition> = emptyList(),
    val verb: List<Definition> = emptyList()
)

data class Definition(
    val definition: String,
    val example: String? = null,
    val synonyms: List<String> = emptyList(),
    val antonyms: List<String> = emptyList()
)