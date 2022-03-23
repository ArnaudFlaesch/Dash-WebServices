package com.dash.utils

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.springframework.http.HttpStatus
import org.springframework.test.web.client.ExpectedCount.never
import org.springframework.test.web.client.ExpectedCount.once
import java.util.stream.Stream

object TestEndpointsArguments {

    fun testTokenArguments(validJwtToken: String): Stream<Arguments> {
        return Stream.of(
            arguments(validJwtToken, 200, once(), "validRequestResponse"),
            arguments("WRONG_TOKEN", 401, never(), "Unauthorized")
        )
    }

    fun testForeignApiCodes(): Stream<Arguments> =
        Stream.of(
            arguments(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), "BadRequestResponse"),
            arguments(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "NotFoundResponse"),
            arguments(HttpStatus.FORBIDDEN, HttpStatus.INTERNAL_SERVER_ERROR.value(), "ForbiddenResponse"),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), "IntenalServerErrorResponse")
        )
}
