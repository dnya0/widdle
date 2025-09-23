package toyproject.widdle.widdle.search.service

interface SearchApi {
    fun search(word: String): Boolean
}