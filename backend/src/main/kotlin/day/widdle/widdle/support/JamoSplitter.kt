package day.widdle.widdle.support

object JamoSplitter {

    fun splitToJamoOrChar(text: String, isKorean: Boolean): List<String> {
        val toCharArray = text.toCharArray()
        if (isKorean) {
            return toCharArray.flatMap { splitHangulChar(it) }
        }
        return toCharArray.map { it.toString() }.toMutableList()
    }

    private fun splitConsonant(ch: Char) = doubleConsonants[ch] ?: listOf(ch)
    private fun splitVowel(ch: Char) = doubleVowels[ch] ?: listOf(ch)

    private fun splitHangulChar(ch: Char): List<String> {
        if (!isHangul(ch)) return listOf(ch.toString())

        val code = ch.code - 0xAC00
        val cho = code / (21 * 28)
        val jung = (code % (21 * 28)) / 28
        val jong = code % 28

        val result = mutableListOf<Char>()
        result.addAll(splitConsonant(CHO[cho]))
        result.addAll(splitVowel(JUNG[jung]))
        JONG[jong]?.let { result.addAll(splitConsonant(it)) }

        return result.map { it.toString() }
    }

    private fun isHangul(ch: Char) = ch.code in 0xAC00..0xD7A3

    private val CHO = arrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )
    private val JUNG = arrayOf(
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    )
    private val JONG = arrayOf(
        null,
        'ㄱ',
        'ㄲ',
        'ㄳ',
        'ㄴ',
        'ㄵ',
        'ㄶ',
        'ㄷ',
        'ㄹ',
        'ㄺ',
        'ㄻ',
        'ㄼ',
        'ㄽ',
        'ㄾ',
        'ㄿ',
        'ㅀ',
        'ㅁ',
        'ㅂ',
        'ㅄ',
        'ㅅ',
        'ㅆ',
        'ㅇ',
        'ㅈ',
        'ㅊ',
        'ㅋ',
        'ㅌ',
        'ㅍ',
        'ㅎ'
    )

    // 겹자음·겹모음 분리 맵
    private val doubleConsonants = mapOf(
        'ㄲ' to listOf('ㄱ', 'ㄱ'),
        'ㄸ' to listOf('ㄷ', 'ㄷ'),
        'ㅃ' to listOf('ㅂ', 'ㅂ'),
        'ㅆ' to listOf('ㅅ', 'ㅅ'),
        'ㅉ' to listOf('ㅈ', 'ㅈ'),
        'ㄳ' to listOf('ㄱ', 'ㅅ'),
        'ㄵ' to listOf('ㄴ', 'ㅈ'),
        'ㄶ' to listOf('ㄴ', 'ㅎ'),
        'ㄺ' to listOf('ㄹ', 'ㄱ'),
        'ㄻ' to listOf('ㄹ', 'ㅁ'),
        'ㄼ' to listOf('ㄹ', 'ㅂ'),
        'ㄽ' to listOf('ㄹ', 'ㅅ'),
        'ㄾ' to listOf('ㄹ', 'ㅌ'),
        'ㄿ' to listOf('ㄹ', 'ㅍ'),
        'ㅀ' to listOf('ㄹ', 'ㅎ'),
        'ㅄ' to listOf('ㅂ', 'ㅅ')
    )

    private val doubleVowels = mapOf(
        'ㅐ' to listOf('ㅏ', 'ㅣ'),
        'ㅒ' to listOf('ㅑ', 'ㅣ'),
        'ㅔ' to listOf('ㅓ', 'ㅣ'),
        'ㅖ' to listOf('ㅕ', 'ㅣ'),
        'ㅘ' to listOf('ㅗ', 'ㅏ'),
        'ㅙ' to listOf('ㅗ', 'ㅐ'),
        'ㅚ' to listOf('ㅗ', 'ㅣ'),
        'ㅝ' to listOf('ㅜ', 'ㅓ'),
        'ㅞ' to listOf('ㅜ', 'ㅔ'),
        'ㅟ' to listOf('ㅜ', 'ㅣ'),
        'ㅢ' to listOf('ㅡ', 'ㅣ')
    )
}