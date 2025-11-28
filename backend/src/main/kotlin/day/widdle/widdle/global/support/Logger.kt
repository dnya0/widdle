package day.widdle.widdle.global.support

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.reflect.KProperty

inline fun <T : Any> T.loggerDelegate(): LoggerDelegate<T> {
    val actualClass = if (this::class.isCompanion) {
        this::class.java.enclosingClass
    } else {
        this::class.java
    }
    return LoggerDelegate(actualClass) as LoggerDelegate<T>
}

class LoggerDelegate<in T : Any>(private val clazz: Class<T>) {
    private val logger by lazy { LoggerFactory.getLogger(clazz) }

    operator fun getValue(thisRef: T, property: KProperty<*>): Logger = logger
}
