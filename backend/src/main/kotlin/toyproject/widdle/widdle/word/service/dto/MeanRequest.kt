package toyproject.widdle.widdle.word.service.dto

class MeansRequest(private val key: String, private val q: String?) {
    private val part: String? = "word"
    private val translated: String? = "n"
    private val advanced: String? = "y"
    private val method: String? = "exact"

    val parameter: String
        get() = "?key=$key&q=$q&part=$part&translated=$translated&advanced=$advanced&method=$method"
}