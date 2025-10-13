package day.widdle.widdle.correction.service.dto.openai

import com.fasterxml.jackson.annotation.JsonProperty

data class OpenAiResponse(
    val id: String,
    @param:JsonProperty("object")
    val objectType: String,
    val createdAt: Int,
    val status: String,
    val error: ErrorResponse?,
    @param:JsonProperty("incomplete_details")
    val incompleteDetails: IncompleteDetails?,
    val instructions: List<String>?
)

data class ErrorResponse(
    val code: String,
    val message: String
)

data class IncompleteDetails(val reason: String)