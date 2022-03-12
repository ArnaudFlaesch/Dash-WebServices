package com.dash.utils

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.springframework.http.HttpStatus
import org.springframework.test.web.client.ExpectedCount
import java.util.stream.Stream

object TestEndpointsArguments {

    fun testTokenArguments(validJwtToken: String): Stream<Arguments> {
        return Stream.of(
            arguments(validJwtToken, 200, ExpectedCount.once()),
            arguments("WRONG_TOKEN", 401, ExpectedCount.never())
        )
    }

    fun testForeignApiCodes(): Stream<Arguments> =
        Stream.of(
            arguments(HttpStatus.OK, HttpStatus.OK.value()),
            arguments(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
            arguments(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
            arguments(HttpStatus.FORBIDDEN, HttpStatus.INTERNAL_SERVER_ERROR.value()),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value())
        )
}
