package com.dash.infra.rest

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException
import java.net.URI
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProxyClientTest {
    private val restClientBuilder =
        RestClient.builder().also(RestClientConfig().errorStatusHandlerCustomizer()::customize)
    private val mockServer = MockRestServiceServer.bindTo(restClientBuilder).build()
    private val proxyClient = ProxyClient(restClientBuilder.build())

    private val testUrl = URI("http://url.com")

    @AfterEach
    fun verifyAndReset() {
        mockServer.verify()
        mockServer.reset()
    }

    @Test
    fun testGetRequest() {
        val response = "response"

        mockServer
            .expect(requestTo(testUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body(response))

        assertEquals(response, proxyClient.get(testUrl, typeReference<String>()))
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testGetRequestErrors(
        statusCode: HttpStatus,
        exceptionClass: Class<Exception>
    ) {
        mockServer
            .expect(requestTo(testUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(statusCode).contentType(MediaType.APPLICATION_JSON))

        assertThrows(exceptionClass) {
            proxyClient.get(testUrl, typeReference<String>())
        }
    }

    @Test
    fun testGetRequestNullResponse() {
        mockServer
            .expect(requestTo(testUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON))

        assertThrows(NotFoundException::class.java) {
            proxyClient.get(testUrl, typeReference<List<String>>())
        }
    }

    fun requestErrorsParams(): Stream<Arguments> =
        Stream.of(
            arguments(HttpStatus.BAD_REQUEST, BadRequestException::class.java),
            arguments(HttpStatus.NOT_FOUND, NotFoundException::class.java),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, InternalServerErrorException::class.java),
            arguments(HttpStatus.BAD_GATEWAY, RestClientException::class.java)
        )
}
