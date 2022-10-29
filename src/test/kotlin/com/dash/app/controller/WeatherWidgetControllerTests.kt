package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.TestEndpointsArguments.testForeignApiCodes
import com.common.utils.TestEndpointsArguments.testTokenArguments
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
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
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WeatherWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    @Value("\${dash.app.OPENWEATHERMAP_API_URL}")
    private lateinit var weatherApiUrl: String

    private val weatherWidgetEndpoint = "/weatherWidget"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdmin(port).accessToken
        mockServer = MockRestServiceServer.createServer(restTemplate)
    }

    @BeforeEach
    fun resetMockServer() {
        mockServer.reset()
    }

    @Nested
    @DisplayName("Get weather tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetWeatherTests {
        @ParameterizedTest
        @MethodSource("testGetTokenArguments")
        fun testGetWeatherData(token: String, statusCode: Int, expectedNumberOfApiRequests: ExpectedCount) {
            mockServer.expect(expectedNumberOfApiRequests, requestTo(matchesPattern("$weatherApiUrl.*")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON))

            given()
                .port(port)
                .header(createAuthenticationHeader(token))
                .param("city", "Paris")
                .`when`()
                .get("$weatherWidgetEndpoint/weather")
                .then().log().all()
                .statusCode(statusCode)
                .log().all()

            mockServer.verify()
        }

        fun testGetTokenArguments(): Stream<Arguments> = testTokenArguments(jwtToken)
    }

    @Nested
    @DisplayName("Get forecast tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetForecastTests {

        @ParameterizedTest
        @MethodSource("testForecastDataArguments")
        fun testForecastData(weatherApiStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$weatherApiUrl.*")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(weatherApiStatusCodeResponse).contentType(MediaType.APPLICATION_JSON))

            given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .param("city", "Paris")
                .`when`()
                .get("$weatherWidgetEndpoint/forecast")
                .then().log().all()
                .statusCode(expectedStatusCode)
                .log().all()

            mockServer.verify()
        }

        fun testForecastDataArguments(): Stream<Arguments> = testForeignApiCodes()
    }
}
