package day.widdle.widdle.word.domain.validator

import day.widdle.widdle.exception.WiddleException
import org.springframework.stereotype.Component

@Component
class WordValidator {

    fun validateJamoSize(isKorean: Boolean, jamo: List<String>) {
        val (expectedLength, message) = if (isKorean) {
            6 to "단어는 6 글자여야 합니다. (현재 ${jamo.size} 글자)"
        } else {
            5 to "Words must be exactly 5 characters (currently ${jamo.size} characters)."
        }

        if (jamo.size != expectedLength) {
            throw WiddleException(message)
        }
    }
}