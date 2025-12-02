package day.widdle.widdle.global.support

import day.widdle.widdle.global.support.JamoSplitter.splitToJamoOrChar

fun String.isKorean(): Boolean = this.toCharArray().any { char -> char.code in 0xAC00..0xD7A3 }

fun String.toJamoList(): List<String> = splitToJamoOrChar(this, this.isKorean())

fun String.toUpperCaseIfEnglish(): String {
    if (isKorean()) {
        return this
    }
    return this.uppercase()
}
