package day.widdle.widdle.logger

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger {
    // 만약 T가 companion object인 경우, enclosingClass를 사용하여 클래스 이름을 가져옴
    if (T::class.isCompanion) {
        return LoggerFactory.getLogger(T::class.java.enclosingClass) // companion object 클래스
    }
    return LoggerFactory.getLogger(T::class.java) // 일반 클래스
}