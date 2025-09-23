package toyproject.widdle.widdle.search.value

import toyproject.widdle.widdle.support.isKorean

enum class DictionaryType {
    NAVER, ENGLISH;

    companion object {
        fun getType(word: String): DictionaryType? {
            return if (word.isKorean()) NAVER else ENGLISH
        }
    }

}
