package day.widdle.widdle.global.support

import day.widdle.widdle.global.exception.WiddleException

object JamoCombiner {

    private val TAG: String = JamoCombiner.javaClass.simpleName

    // First '가' : 0xAC00(44032), 끝 '힟' : 0xD79F(55199)
    private const val FIRST_HANGUL: Int = 44032

    // 19 initial consonants
    private val CHOSUNG_LIST: CharArray = charArrayOf(
        'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ',
        'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    private const val JUNGSUNG_COUNT: Int = 21

    // 21 vowels
    private val JUNGSUNG_LIST: CharArray = charArrayOf(
        'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ',
        'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ',
        'ㅣ'
    )

    private const val JONGSUNG_COUNT: Int = 28

    // 28 consonants placed under a vowel(plus one empty character)
    private val JONGSUNG_LIST: CharArray = charArrayOf(
        ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ',
        'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ',
        'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    )

    private val DOUBLE_VOWELS = mapOf(
        listOf("ㅏ", "ㅣ") to "ㅐ",
        listOf("ㅑ", "ㅣ") to "ㅒ",
        listOf("ㅓ", "ㅣ") to "ㅔ",
        listOf("ㅕ", "ㅣ") to "ㅖ",
        listOf("ㅗ", "ㅏ") to "ㅘ",
        listOf("ㅗ", "ㅐ") to "ㅙ",
        listOf("ㅗ", "ㅣ") to "ㅚ",
        listOf("ㅜ", "ㅓ") to "ㅝ",
        listOf("ㅜ", "ㅔ") to "ㅞ",
        listOf("ㅜ", "ㅣ") to "ㅟ",
        listOf("ㅡ", "ㅣ") to "ㅢ"
    )

    private val DOUBLE_INITIALS = mapOf(
        listOf("ㄱ", "ㄱ") to "ㄲ",
        listOf("ㄷ", "ㄷ") to "ㄸ",
        listOf("ㅂ", "ㅂ") to "ㅃ",
        listOf("ㅅ", "ㅅ") to "ㅆ",
        listOf("ㅈ", "ㅈ") to "ㅉ"
    )

    private val DOUBLE_FINALS = mapOf(
        listOf("ㄱ", "ㅅ") to "ㄳ",
        listOf("ㄴ", "ㅈ") to "ㄵ",
        listOf("ㄴ", "ㅎ") to "ㄶ",
        listOf("ㄹ", "ㄱ") to "ㄺ",
        listOf("ㄹ", "ㅁ") to "ㄻ",
        listOf("ㄹ", "ㅂ") to "ㄼ",
        listOf("ㄹ", "ㅅ") to "ㄽ",
        listOf("ㄹ", "ㅌ") to "ㄾ",
        listOf("ㄹ", "ㅍ") to "ㄿ",
        listOf("ㄹ", "ㅎ") to "ㅀ",
        listOf("ㅂ", "ㅅ") to "ㅄ"
    )

    fun preprocessJamo(jamoList: List<String>): List<String> {
        val mergedVowels = mergeDoubleVowels(jamoList)
        val mergedConsonants = mergeDoubleConsonants(mergedVowels)
        return mergedConsonants
    }

    fun assembleWithMergedVowels(jamoList: List<String>): String = assemble(mergeDoubleVowels(jamoList))

    fun assemble(jamoList: List<String>): String {
        if (jamoList.isNotEmpty()) {
            var result = ""
            var startIdx = 0

            while (true) {
                if (startIdx < jamoList.size) {
                    val assembleSize = getNextAssembleSize(jamoList, startIdx);
                    result += assemble(jamoList, startIdx, assembleSize);
                    startIdx += assembleSize;
                } else {
                    break;
                }
            }

            return result
        } else {
            throw WiddleException("자소가 없습니다");
        }
    }

    private fun assemble(jamoList: List<String>, startIdx: Int, assembleSize: Int): String {
        var unicode = FIRST_HANGUL

        val chosungIndex = String(CHOSUNG_LIST).indexOf(jamoList.get(startIdx));

        if (chosungIndex >= 0) {
            unicode += JONGSUNG_COUNT * JUNGSUNG_COUNT * chosungIndex;
        } else {
            throw WiddleException("${startIdx + 1}번째 자소가 한글 초성이 아닙니다");
        }

        val jungsungIndex = String(JUNGSUNG_LIST).indexOf(jamoList.get(startIdx + 1));

        if (jungsungIndex >= 0) {
            unicode += JONGSUNG_COUNT * jungsungIndex;
        } else {
            throw WiddleException("${startIdx + 2}번째 자소가 한글 중성이 아닙니다");
        }

        if (assembleSize > 2) {
            val jongsungIndex = String(JONGSUNG_LIST).indexOf(jamoList.get(startIdx + 2));

            if (jongsungIndex >= 0) {
                unicode += jongsungIndex;
            } else {
                throw WiddleException("${startIdx + 3}번째 자소가 한글 종성이 아닙니다");
            }
        }

        return Char(unicode).toString();
    }

    fun mergeDoubleVowels(jamoList: List<String>): List<String> {
        val merged = mutableListOf<String>()
        var i = 0

        while (i < jamoList.size) {
            if (i + 1 < jamoList.size) {
                val pair = listOf(jamoList[i], jamoList[i + 1])
                val mergedChar = DOUBLE_VOWELS[pair]
                if (mergedChar != null) {
                    merged.add(mergedChar)
                    i += 2
                    continue
                }
            }

            merged.add(jamoList[i])
            i++
        }

        return merged
    }

    fun mergeDoubleConsonants(jamoList: List<String>): List<String> {
        val result = mutableListOf<String>()
        var i = 0

        while (i < jamoList.size) {
            // 초성 위치 (i == 0 or i % assembleSize == 0)
            val current = jamoList[i]
            val next = jamoList.getOrNull(i + 1)

            val isInitial = i == 0 || (i - result.size) % 3 == 0
            val isFinalCandidate = (i - result.size) % 3 == 2

            val combined = when {
                isInitial && next != null -> DOUBLE_INITIALS[listOf(current, next)]
                isFinalCandidate && next != null -> DOUBLE_FINALS[listOf(current, next)]
                else -> null
            }

            if (combined != null) {
                result.add(combined)
                i += 2
            } else {
                result.add(current)
                i++
            }
        }

        return result
    }

    private fun getNextAssembleSize(jamoList: List<String>, startIdx: Int): Int {
        val remainJamoLength = jamoList.size - startIdx
        var assembleSize = 0

        if (remainJamoLength > 3) {
            if (String(JUNGSUNG_LIST).contains(jamoList.get(startIdx + 3))) {
                assembleSize = 2;
            } else {
                assembleSize = 3;
            }
        } else if (remainJamoLength == 3 || remainJamoLength == 2) {
            assembleSize = remainJamoLength;
        } else {
            throw WiddleException("한글을 구성할 자소가 부족하거나 한글이 아닌 문자가 있습니다")
        }

        return assembleSize;
    }

}
