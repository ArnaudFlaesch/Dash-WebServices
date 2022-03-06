package com.dash.controller

import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.client.ClientHttpResponse
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.client.ResponseErrorHandler
import org.springframework.web.server.ServerErrorException
import java.io.IOException
import java.net.URI

class ErrorHandler : ResponseErrorHandler {
    @Throws(IOException::class)
    override fun handleError(url: URI, method: HttpMethod, response: ClientHttpResponse) {
        super.handleError(url, method, response)
    }

    @Throws(IOException::class)
    override fun hasError(response: ClientHttpResponse): Boolean {
        return (response.statusCode.is4xxClientError || response.statusCode.is5xxServerError)
    }

    override fun handleError(response: ClientHttpResponse) {
        when (response.statusCode) {
            HttpStatus.BAD_REQUEST -> throw BadRequestException()
            HttpStatus.NOT_FOUND -> throw NotFoundException()
            HttpStatus.INTERNAL_SERVER_ERROR -> throw ServerErrorException("Erreur", Error("error23"))
        }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "bad requessst")
    class BadRequestException : Exception()

    @ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Not found")
    class NotFoundException : Exception()
}
