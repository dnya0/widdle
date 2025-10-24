package day.widdle.widdle.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class WiddleExceptionHandler {

    @ExceptionHandler(WiddleException::class)
    fun handleWiddleException(ex: WiddleException): ResponseEntity<String> = ex.toErrorResponse()

    fun WiddleException.toErrorResponse(status: HttpStatus = HttpStatus.BAD_REQUEST): ResponseEntity<String> =
        ResponseEntity.status(status).body(this.message)
}