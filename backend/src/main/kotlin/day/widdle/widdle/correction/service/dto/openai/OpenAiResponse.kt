package day.widdle.widdle.correction.service.dto.openai

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiResponse(
    val id: String,
    @param:JsonProperty("object")
    val objectType: String,
    @param:JsonProperty("create_at")
    val createdAt: Long,
    val status: String,
    val error: ErrorResponse?,
    @param:JsonProperty("incomplete_details")
    val incompleteDetails: IncompleteDetails?,
    val instructions: String?,
    val model: String,
    val output: List<OpenAiOutput>
)

data class ErrorResponse(
    val code: String,
    val message: String
)

data class IncompleteDetails(val reason: String)

data class OpenAiOutput(
    val type: String,
    val id: String,
    val value: String,
    val annotations: List<String>,
    val content: Content
)

data class Content(
    val type: String,
    val text: String,
    val annotations: List<String>,
)
