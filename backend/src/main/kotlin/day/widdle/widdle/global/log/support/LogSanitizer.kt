package day.widdle.widdle.global.log.support

interface LogSanitizer {

    fun sanitize(value: Any?): String

}
