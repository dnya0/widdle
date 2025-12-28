package day.widdle.widdle.global.support

import day.widdle.widdle.global.support.JamoSplitter.splitToJamoOrChar

fun String.containsHangul(): Boolean = this.toCharArray().any { char -> char.code in 0xAC00..0xD7A3 }

fun String.isKoreanCode(): Boolean = this.lowercase() == "kr"

fun String.toJamoList(): List<String> = splitToJamoOrChar(this, this.containsHangul())

fun String.toUpperCaseIfEnglish(): String {
    return if (containsHangul()) this else this.uppercase()
}
