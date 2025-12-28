package day.widdle.widdle.global.exception

import day.widdle.widdle.global.base.ResponseData
import day.widdle.widdle.global.support.loggerDelegate
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WiddleExceptionHandler {

    private val log by loggerDelegate()

    @ExceptionHandler(WiddleException::class)
    fun handleWiddleException(ex: WiddleException): ResponseData<Boolean> = ex.toErrorResponse()

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseData<Boolean> {
        log.error("🧨 Invalid argument: {}", ex.message)
        return ex.toErrorResponse(BAD_REQUEST, "잘못된 요청입니다.")
    }

    @ExceptionHandler(Exception::class)
    fun handleException(ex: Exception): ResponseData<Boolean> {
        log.error("🧨 Unexpected exception occurred", ex)
        return ex.toErrorResponse(INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.")
    }

    private fun Exception.toErrorResponse(
        status: HttpStatus = BAD_REQUEST, message: String? = this.message
    ): ResponseData<Boolean> = ResponseData(status, message, false)
}
