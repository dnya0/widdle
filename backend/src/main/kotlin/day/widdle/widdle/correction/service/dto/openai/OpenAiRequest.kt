package day.widdle.widdle.correction.service.dto.openai

data class OpenAiRequest(
    val model: String,
    val input: String? = null,
    val prompt: Prompt
) {
    companion object {
        fun of(id: String, word: String): OpenAiRequest = OpenAiRequest(
            model = "gpt-4o-mini",
            prompt = Prompt(
                id = id,
                variables = mapOf("word" to word),
                version = "1"
            )
        )
    }
}

/**
 * ### Prompt Example
 *
 * ```
 * curl https://api.openai.com/v1/responses \
 *   -H "Authorization: Bearer $OPENAI_API_KEY" \
 *   -H "Content-Type: application/json" \
 *   -d '{
 *     "model": "gpt-5",
 *     "prompt": {
 *       "id": "pmpt_abc123",
 *       "version": "2",
 *       "variables": {
 *         "customer_name": "Jane Doe",
 *         "product": "40oz juice box"
 *       }
 *     }
 *   }'
 * ```
 */
data class Prompt(
    val id: String,//required
    val variables: Map<String, String>?,
    val version: String,
)