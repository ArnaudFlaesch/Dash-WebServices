package com.dash.service

import com.dash.controller.ErrorHandler
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.client.ExpectedCount
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers.method
import org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo
import org.springframework.test.web.client.response.MockRestResponseCreators.withStatus
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.stream.Stream

@SpringBootTest
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ProxyServiceTest {

    @Autowired
    private lateinit var proxyService: ProxyService

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

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
        mockServer.expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON).body(response)
            )
        val actualResponse = proxyService.getDataFromProxy(testUrl)
        assertEquals(response, actualResponse)
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testGetRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        mockServer.expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(
                withStatus(statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(exceptionClass) {
            proxyService.getDataFromProxy(testUrl)
        }
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testPostRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        mockServer.expect(ExpectedCount.once(), requestTo(URI(testUrl)))
            .andExpect(method(HttpMethod.POST))
            .andRespond(
                withStatus(statusCode)
                    .contentType(MediaType.APPLICATION_JSON)
            )
        assertThrows(exceptionClass) {
            proxyService.postDataFromProxy(testUrl, "{}")
        }
        mockServer.verify()
    }

    fun requestErrorsParams(): Stream<Arguments> {
        return Stream.of(
            arguments(HttpStatus.BAD_REQUEST, ErrorHandler.BadRequestException::class.java),
            arguments(HttpStatus.NOT_FOUND, ErrorHandler.NotFoundException::class.java),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, ErrorHandler.InternalServerErrorException::class.java)
        )
    }
}
