package com.dash.infra.rest

import com.common.utils.AbstractIT
import com.dash.app.controller.ErrorHandler
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForEntity
import java.util.stream.Stream

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestClientTest : AbstractIT() {

    @Autowired
    private lateinit var proxyService: RestClient

    @Autowired
    private lateinit var restTemplate: RestTemplate

    val testUrl = "http://url.com"

    @Test
    fun testGetRequest() {
        val response = "response"

        Mockito.`when`(restTemplate.getForEntity<String>(testUrl)).thenReturn(
            ResponseEntity(response, HttpStatus.OK)
        )

        val actualResponse = proxyService.getDataFromProxy(testUrl, String::class)
        assertEquals(response, actualResponse)
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testGetRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        Mockito.`when`(restTemplate.getForEntity<String>(testUrl)).thenReturn(ResponseEntity(statusCode))

        assertThrows(exceptionClass) { proxyService.getDataFromProxy(testUrl, String::class) }
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testPostRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        Mockito.`when`(restTemplate.postForObject<ResponseEntity<String>>(anyString(), any(), any()))
            .thenReturn(ResponseEntity(statusCode))

        assertThrows(exceptionClass) { proxyService.postDataFromProxy(testUrl, "{}", String::class) }
    }

    fun requestErrorsParams(): Stream<Arguments> {
        return Stream.of(
            arguments(HttpStatus.BAD_REQUEST, ErrorHandler.BadRequestException::class.java),
            arguments(HttpStatus.NOT_FOUND, ErrorHandler.NotFoundException::class.java),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, ErrorHandler.InternalServerErrorException::class.java)
        )
    }
}
