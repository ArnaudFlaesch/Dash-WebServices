package com.dash.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.TestEndpointsArguments
import com.dash.controller.requests.stravaWidget.GetStravaRefreshTokenPayload
import com.dash.controller.requests.stravaWidget.GetStravaTokenPayload
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
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
class StravaWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    private val stravaApiUrlMatcher = "https://www.strava.com/.*"
    private val stravaWidgetEndpoint = "/stravaWidget"

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

    @ParameterizedTest
    @MethodSource("testGetTokenArguments")
    fun testGetToken(token: String, statusCode: Int, expectedNumberOfApiRequests: ExpectedCount) {
        mockServer.expect(expectedNumberOfApiRequests, requestTo(matchesPattern(stravaApiUrlMatcher)))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON))

        val getStravaTokenPayload = GetStravaTokenPayload("api_code")

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $token"))
            .`when`()
            .body(getStravaTokenPayload)
            .post("$stravaWidgetEndpoint/getToken")
            .then().log().all()
            .statusCode(statusCode)
            .log().all()

        mockServer.verify()
    }

    @ParameterizedTest
    @MethodSource("testGetRefreshTokenArguments")
    fun testGetRefreshToken(stravaApiStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
        mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(stravaApiUrlMatcher)))
            .andExpect(method(HttpMethod.POST))
            .andRespond(withStatus(stravaApiStatusCodeResponse).contentType(MediaType.APPLICATION_JSON))

        val getStravaRefreshTokenPayload = GetStravaRefreshTokenPayload("refresh_token")

        given()
            .port(port)
            .contentType(ContentType.JSON)
            .header(Header("Authorization", "Bearer $jwtToken"))
            .`when`()
            .body(getStravaRefreshTokenPayload)
            .post("$stravaWidgetEndpoint/getRefreshToken")
            .then().log().all()
            .statusCode(expectedStatusCode)
            .log().all()

        mockServer.verify()
    }

    fun testGetTokenArguments(): Stream<Arguments> = TestEndpointsArguments.testTokenArguments(jwtToken)
    fun testGetRefreshTokenArguments(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()
}
