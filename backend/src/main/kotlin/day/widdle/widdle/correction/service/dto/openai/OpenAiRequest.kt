package day.widdle.widdle.correction.service.dto.openai

data class OpenAiRequest(
    val model: String,
    val input: String,
    val prompt: Prompt
)

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
    val variables: Map<String, String>,
    val version: String,
)
