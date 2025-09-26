package day.widdle.widdle.support

fun String.isKorean(): Boolean = this.toCharArray().all { char -> char.code in 0xAC00..0xD7A3 }