package com.dash.infra.rest

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.restclient.test.autoconfigure.AutoConfigureMockRestServiceServer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestClientException
import java.net.URI
import java.util.stream.Stream

@SpringBootTest
@AutoConfigureMockRestServiceServer
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProxyClientTest {
    @Autowired
    private lateinit var proxyClient: ProxyClient

    @Autowired
    private lateinit var mockServer: MockRestServiceServer

    private val testUrl = URI("http://url.com")

    @Test
    fun testGetRequest() {
        val response = "response"

        mockServer
            .expect(ExpectedCount.once(), requestTo(testUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(response)
            )
        val actualResponse = proxyClient.get(testUrl, typeReference<String>())
        assertEquals(response, actualResponse)
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testGetRequestErrors(
        statusCode: HttpStatus,
        exceptionClass: Class<Exception>
    ) {
        mockServer
            .expect(ExpectedCount.once(), requestTo(testUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(exceptionClass) {
            proxyClient.get(testUrl, typeReference<String>())
        }
        mockServer.verify()
    }

    @Test
    fun testGetRequestNullResponse() {
        mockServer
            .expect(ExpectedCount.once(), requestTo(testUrl))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(NotFoundException::class.java) {
            proxyClient.get(testUrl, typeReference<List<String>>())
        }
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testPostRequestErrors(
        statusCode: HttpStatus,
        exceptionClass: Class<Exception>
    ) {
        mockServer
            .expect(ExpectedCount.once(), requestTo(testUrl))
            .andExpect(method(HttpMethod.POST))
            .andRespond(
                withStatus(statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(exceptionClass) {
            proxyClient.post(testUrl, "{}", typeReference<String>())
        }
        mockServer.verify()
    }

    @Test
    fun testBuildUriEncodesQueryParams() {
        assertEquals(
            URI("http://url.com/api/weather?q=Saint%20Denis&units=metric"),
            buildUri("http://url.com/api", "/weather", "q" to "Saint Denis", "units" to "metric")
        )
    }

    fun requestErrorsParams(): Stream<Arguments> =
        Stream.of(
            arguments(HttpStatus.BAD_REQUEST, BadRequestException::class.java),
            arguments(HttpStatus.NOT_FOUND, NotFoundException::class.java),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, InternalServerErrorException::class.java),
            arguments(HttpStatus.BAD_GATEWAY, RestClientException::class.java)
        )
}
