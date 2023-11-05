package com.dash.app.controller

import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.client.RestClientException
import java.io.IOException

class ErrorHandler : ResponseErrorHandler {
    companion object {
        @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Bad request")
        class BadRequestException : Exception()

        @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found")
        class NotFoundException : Exception()

        @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal server error")
        class InternalServerErrorException : Exception()
    }

    @Throws(IOException::class)
    override fun hasError(response: ClientHttpResponse): Boolean {
        return (response.statusCode.is4xxClientError || response.statusCode.is5xxServerError)
    }

    override fun handleError(response: ClientHttpResponse) {
        when (response.statusCode) {
            HttpStatus.BAD_REQUEST -> throw BadRequestException()
            HttpStatus.NOT_FOUND -> throw NotFoundException()
            HttpStatus.INTERNAL_SERVER_ERROR -> throw InternalServerErrorException()
            else -> throw RestClientException(response.statusCode.toString())
        }
    }
}
