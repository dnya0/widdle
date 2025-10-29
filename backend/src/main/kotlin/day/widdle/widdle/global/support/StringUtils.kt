package day.widdle.widdle.global.support

import day.widdle.widdle.global.support.JamoSplitter.splitToJamoOrChar

fun String.isKorean(): Boolean = this.toCharArray().all { char -> char.code in 0xAC00..0xD7A3 }

fun String.toJamoList(): List<String> = splitToJamoOrChar(this, this.isKorean())
