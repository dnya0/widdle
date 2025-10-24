package day.widdle.widdle.search.service

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

interface SearchApi {

    suspend fun search(word: String): Boolean
    fun encode(word: String): String = URLEncoder.encode(word, StandardCharsets.UTF_8)

}