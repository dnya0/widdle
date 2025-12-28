package day.widdle.widdle.search.value

import day.widdle.widdle.search.value.DictionaryType.ENGLISH
import day.widdle.widdle.search.value.DictionaryType.KOREAN
import day.widdle.widdle.global.support.containsHangul

enum class DictionaryType {
    NAVER, ENGLISH, KOREAN;
}

// TODO: Decide between NAVER and KOREAN dictionary APIs
fun String.getDictionaryType(): DictionaryType {
    return if (this.containsHangul()) KOREAN else ENGLISH
}
