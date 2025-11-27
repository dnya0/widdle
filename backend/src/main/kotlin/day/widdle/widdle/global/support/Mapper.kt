package day.widdle.widdle.global.support

import day.widdle.widdle.global.base.ResponseData
import org.springframework.http.HttpStatus

fun <T> T.toResponse(status: HttpStatus = HttpStatus.OK): ResponseData<T> =
    ResponseData(status, this)
