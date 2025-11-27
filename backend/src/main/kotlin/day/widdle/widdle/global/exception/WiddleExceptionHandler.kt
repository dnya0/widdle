package day.widdle.widdle.global.exception

import day.widdle.widdle.global.base.ResponseData
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WiddleExceptionHandler {

    @ExceptionHandler(WiddleException::class)
    fun handleWiddleException(ex: WiddleException): ResponseData<Boolean> = ex.toErrorResponse()

    private fun WiddleException.toErrorResponse(status: HttpStatus = BAD_REQUEST): ResponseData<Boolean> =
        ResponseData(status, this.message, false)
}
