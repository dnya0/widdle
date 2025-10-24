package day.widdle.widdle.correction.service.dto.bareun

data class CorrectErrorRequest(
    val document: Document,
    val encodingType: EncodingType = EncodingType.UTF16,
    val customDictNames: List<String> = emptyList(),
    val config: RevisionConfig = RevisionConfig(),
) {
    companion object {
        fun create(word: String) = CorrectErrorRequest(document = Document(content = word))
    }

}

data class Document(
    val content: String,
    val language: String = "ko_KR"
)

enum class EncodingType {
    UTF8,
    UTF16,
    UTF32
}

data class RevisionConfig(
    val disableSplitSentence: Boolean = false,
    val disableCaretSpacing: Boolean = false,
    val disableVxSpacing: Boolean = false,
    val treatAsTitle: Boolean = false,
    val enableLimitedPunctuation: Boolean = false,
    val enableCleanupWhitespace: Boolean = false,
    val enableSentenceCheck: Boolean = false
)