package toyproject.widdle.widdle.service

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import toyproject.widdle.widdle.service.dto.MeansRequest
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

        val req: MeansRequest = MeansRequest(key, encodedWord)
        val result = StringBuilder()

        val url: URL = URL(baseUrl + req.parameter)
        val urlConnection: HttpURLConnection? = url.openConnection() as HttpURLConnection?

        urlConnection?.let {
            it.requestMethod = "GET"
            it.connect()
        }

    }

}