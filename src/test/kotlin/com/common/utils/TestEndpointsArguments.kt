package com.common.utils

import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import java.util.stream.Stream

object TestEndpointsArguments {
    fun testTokenArguments(validJwtToken: String): Stream<Arguments> =
        Stream.of(
            arguments(validJwtToken, 200),
            arguments("WRONG_TOKEN", 401)
        )
}
