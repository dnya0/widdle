package day.widdle.widdle.search.value

import day.widdle.widdle.search.value.DictionaryType.ENGLISH
import day.widdle.widdle.search.value.DictionaryType.KOREAN
import day.widdle.widdle.support.isKorean

enum class DictionaryType {
    NAVER, ENGLISH, KOREAN;
}

fun String.getDictionaryType(): DictionaryType {
    return if (this.isKorean()) KOREAN else ENGLISH
}
