package day.widdle.widdle.global.support

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty

@Suppress("UNCHECKED_CAST")
fun <T : Any> T.loggerDelegate(): LoggerDelegate<T> {
    val actualClass = (if (this::class.isCompanion) {
        this::class.java.enclosingClass ?: this::class.java
    } else {
        this::class.java
    }) as Class<T>

    return LoggerDelegate(actualClass)
}

class LoggerDelegate<in T : Any>(private val clazz: Class<T>) {
    private val logger by lazy { LoggerFactory.getLogger(clazz) }

    operator fun getValue(thisRef: T, property: KProperty<*>): Logger = logger
}
