package toyproject.widdle.widdle.support

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun String.isKorean(): Boolean = this.toCharArray().all { char -> char.code in 0xAC00..0xD7A3 }

fun encode(word: String) = runCatching { URLEncoder.encode(word, StandardCharsets.UTF_8) }