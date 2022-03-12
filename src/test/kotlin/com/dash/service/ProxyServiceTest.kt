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
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
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
        val url = "http://url.com"
        val response = "response"
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(URI(url)))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withStatus(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON).body(response))
        val actualResponse = proxyService.getDataFromProxy(url)
        assertEquals(response, actualResponse)
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testGetRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        val url = "http://url.com"
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(URI(url)))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
            .andRespond(MockRestResponseCreators.withStatus(statusCode)
                .contentType(MediaType.APPLICATION_JSON))
        assertThrows(exceptionClass) {
            proxyService.getDataFromProxy(url)
        }
        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("requestErrorsParams")
    fun testPostRequestErrors(statusCode: HttpStatus, exceptionClass: Class<Exception>) {
        val url = "http://url.com"
        mockServer.expect(ExpectedCount.once(), MockRestRequestMatchers.requestTo(URI(url)))
            .andExpect(MockRestRequestMatchers.method(HttpMethod.POST))
            .andRespond(MockRestResponseCreators.withStatus(statusCode)
                .contentType(MediaType.APPLICATION_JSON))
        assertThrows(exceptionClass) {
            proxyService.postDataFromProxy(url, "")
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