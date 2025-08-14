package toyproject.widdle.widdle.support

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

fun <T> T.toResponse(status: HttpStatus = HttpStatus.OK): ResponseEntity<T> =
    ResponseEntity.status(status).body(this)