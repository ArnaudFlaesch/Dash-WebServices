package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.TestEndpointsArguments
import com.dash.app.controller.requests.stravaWidget.GetStravaRefreshTokenPayload
import com.dash.app.controller.requests.stravaWidget.GetStravaTokenPayload
import com.dash.domain.model.stravaWidget.StravaActivityDomain
import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.dash.infra.api.response.StravaApiResponse
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
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
class StravaWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    @Value("\${dash.app.STRAVA_API_URL}")
    private lateinit var stravaApiUrl: String

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

    @Nested
    @DisplayName("Get token tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetTokenTests {

        @ParameterizedTest
        @MethodSource("testGetTokenArguments")
        fun should_get_token(token: String, statusCode: Int, expectedNumberOfApiRequests: ExpectedCount) {
            mockServer.expect(expectedNumberOfApiRequests, requestTo(matchesPattern("$stravaApiUrl/.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(
                    withStatus(HttpStatus.OK)
                        .body(StravaApiResponse.stravaTokenResponse)
                        .contentType(MediaType.APPLICATION_JSON)
                )

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

        fun testGetTokenArguments(): Stream<Arguments> = TestEndpointsArguments.testTokenArguments(jwtToken)
    }

    @Nested
    @DisplayName("Get refresh token tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetRefreshTokenTests {

        @ParameterizedTest
        @MethodSource("testGetRefreshTokenArguments")
        fun should_get_refresh_token(stravaApiStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$stravaApiUrl/.*")))
                .andExpect(method(HttpMethod.POST))
                .andRespond(withStatus(stravaApiStatusCodeResponse).contentType(MediaType.APPLICATION_JSON))

            val getStravaRefreshTokenPayload = GetStravaRefreshTokenPayload("refresh_token")

            given()
                .port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
                .`when`()
                .body(getStravaRefreshTokenPayload)
                .post("$stravaWidgetEndpoint/getRefreshToken")
                .then().log().all()
                .statusCode(expectedStatusCode)
                .log().all()

            mockServer.verify()
        }

        fun testGetRefreshTokenArguments(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()
    }

    @Nested
    @DisplayName("Get athlete data tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAthleteDataTests {

        @Test
        fun should_get_athlete_data() {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$stravaApiUrl/.*")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                    withStatus(HttpStatus.OK)
                        .body(StravaApiResponse.stravaAthleteData)
                        .contentType(MediaType.APPLICATION_JSON)
                )

            val actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
                .param("token", "VALID_TOKEN")
                .`when`()
                .get("$stravaWidgetEndpoint/getAthleteData")
                .then().log().all()
                .statusCode(200)
                .log().all()
                .extract().`as`(StravaAthleteDomain::class.java)

            mockServer.verify()

            assertEquals("aflaesch", actual.username)
        }
    }

    @Nested
    @DisplayName("Get athlete activities tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAthleteActivitiesTests {

        @Test
        fun should_get_athlete_activities() {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$stravaApiUrl/.*")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                    withStatus(HttpStatus.OK)
                        .body(StravaApiResponse.stravaActivitiesData)
                        .contentType(MediaType.APPLICATION_JSON)
                )

            val actual = given()
                .port(port)
                .contentType(ContentType.JSON)
                .header(createAuthenticationHeader(jwtToken))
                .param("token", "VALID_TOKEN")
                .`when`()
                .get("$stravaWidgetEndpoint/getAthleteActivities")
                .then().log().all()
                .statusCode(200)
                .log().all()
                .extract().`as`(object : TypeRef<List<StravaActivityDomain>>() {})

            mockServer.verify()

            assertEquals(6, actual.size)
        }
    }
}
