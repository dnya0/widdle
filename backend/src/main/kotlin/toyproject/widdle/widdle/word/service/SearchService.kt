package toyproject.widdle.widdle.word.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import toyproject.widdle.widdle.word.service.dto.MeansRequest
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Service
class SearchService {
    val key = ""

    @Transactional(readOnly = true)
    fun sendRequest(word: String) {
        val baseUrl = "https://krdict.korean.go.kr/api/search"
        val encodedWord = URLEncoder.encode(word, StandardCharsets.UTF_8)

        val req = MeansRequest(key, encodedWord)
        val result = StringBuilder()

        val url: URL = URL(baseUrl + req.parameter)
        val urlConnection = url.openConnection() as HttpURLConnection

        urlConnection.requestMethod = "GET"
        urlConnection.connect()

        urlConnection.inputStream.use {
            val br = BufferedReader(InputStreamReader(it, "UTF-8"))

            var returnLine: String?
            while ((br.readLine().also { returnLine = it }) != null) {
                result.append(returnLine)
            }
        }

        val jsonObject= XML.toJSONObject(result.toString())

    }

}