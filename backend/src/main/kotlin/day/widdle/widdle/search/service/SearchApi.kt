package day.widdle.widdle.search.service

interface SearchApi {
    suspend fun search(word: String): Boolean
}