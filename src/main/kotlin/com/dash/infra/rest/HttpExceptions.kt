package com.dash.infra.rest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad request")
class BadRequestException : RuntimeException()

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found")
class NotFoundException : RuntimeException()

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
class InternalServerErrorException : RuntimeException()

/** Treats an empty response body as a missing resource. */
fun <T : Any> T?.orNotFound(): T = this ?: throw NotFoundException()
