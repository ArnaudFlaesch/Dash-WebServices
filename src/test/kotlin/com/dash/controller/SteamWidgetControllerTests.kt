package com.dash.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.TestEndpointsArguments
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
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
class SteamWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    private val steamApiUrlMatcher = "https://api.steampowered.com.*"
    private val steamWidgetEndpoint = "/steamWidget"

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
    @DisplayName("Get player summary tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetPlayerSummaryTests {
        @Test
        fun testGetPlayerSummary() {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(steamApiUrlMatcher)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("test"))

            given()
                .port(port)
                .header(Header("Authorization", "Bearer $jwtToken"))
                .`when`()
                .get("$steamWidgetEndpoint/playerData")
                .then().log().all()
                .statusCode(200)
                .log().all()

            mockServer.verify()
        }
    }

    @Nested
    @DisplayName("Get owned games tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetOwnedGamesTests {

        @ParameterizedTest
        @MethodSource("testGetOwnedGamesArguments")
        fun testGetOwnedGames(steamApiStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(steamApiUrlMatcher)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(steamApiStatusCodeResponse).contentType(MediaType.APPLICATION_JSON))

            given()
                .port(port)
                .header(Header("Authorization", "Bearer $jwtToken"))
                .`when`()
                .get("$steamWidgetEndpoint/ownedGames")
                .then().log().all()
                .statusCode(expectedStatusCode)
                .log().all()

            mockServer.verify()
        }

        fun testGetOwnedGamesArguments(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()
    }

    @Nested
    @DisplayName("Get achievement list tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAchievementListTests {
        @ParameterizedTest
        @MethodSource("testGetAchievementListArguments")
        fun testGetAchievementList(
            token: String,
            statusCode: Int,
            expectedNumberOfApiRequests: ExpectedCount,
            expectedResponse: String
        ) {
            mockServer.expect(expectedNumberOfApiRequests, requestTo(matchesPattern(steamApiUrlMatcher)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                    withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body(expectedResponse)
                )

            given()
                .port(port)
                .header(Header("Authorization", "Bearer $token"))
                .param("appId", 1337)
                .`when`()
                .get("$steamWidgetEndpoint/achievementList")
                .then().log().all()
                .statusCode(statusCode)
                .log().all()
                .body("$", Matchers.notNullValue())

            mockServer.verify()
        }

        fun testGetAchievementListArguments(): Stream<Arguments> = TestEndpointsArguments.testTokenArguments(jwtToken)
    }
}
