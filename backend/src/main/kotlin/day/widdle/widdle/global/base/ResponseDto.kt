package day.widdle.widdle.global.base

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.OK

data class ResponseData<T>(
    val code: Int,
    val status: String,
    val message: String?,
    val data: T? = null
) {

    constructor(status: HttpStatus, message: String?, data: T?) : this(status.value(), status.name, message, data)
    constructor(status: HttpStatus, data: T?): this(status.value(), status.name, null, data)
    constructor(data: T): this(OK.value(), OK.name, null, data)
    constructor(message: String, data: T?) : this(OK.value(), OK.name, message, data)
}
