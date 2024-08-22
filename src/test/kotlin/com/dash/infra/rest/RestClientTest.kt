package com.dash.infra.rest

import com.common.utils.AbstractIT
import com.dash.app.controller.ErrorHandler
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.*
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.stream.Stream

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RestClientTest : AbstractIT() {
    @Autowired
    private lateinit var restClient: RestClient

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var mockServer: MockRestServiceServer

    val testUrl = "http://url.com"

    @BeforeAll
    fun setup() {
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @BeforeEach
    fun resetMockServer() {
        mockServer.reset()
    }

    @Test
    fun testGetRequest() {
        val response = "response"

        mockServer
            .expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(response)
            )
        val actualResponse = restClient.getDataFromProxy(testUrl, String::class)
        assertEquals(response, actualResponse)
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testGetRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        mockServer
            .expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(exceptionClass) {
            restClient.getDataFromProxy(testUrl, String::class)
        }
        mockServer.verify()
    }

    @Test
    fun testGetRequestNullResponse() {
        mockServer
            .expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(ErrorHandler.Companion.NotFoundException::class.java) {
            restClient.getDataFromProxy(
                testUrl,
                object : ParameterizedTypeReference<List<String>>() {},
                HttpEntity<List<String>>(HttpHeaders())
            )
        }
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testPostRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        mockServer
            .expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.POST))
            .andRespond(
                withStatus(statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(exceptionClass) {
            restClient.postDataFromProxy(testUrl, "{}", String::class)
        }
        mockServer.verify()
    }

    fun requestErrorsParams(): Stream<Arguments> =
        Stream.of(
            arguments(
                HttpStatus.BAD_REQUEST,
                ErrorHandler.Companion.BadRequestException::class.java
            ),
            arguments(HttpStatus.NOT_FOUND, ErrorHandler.Companion.NotFoundException::class.java),
            arguments(
                HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorHandler.Companion.InternalServerErrorException::class.java
            )
        )
}
