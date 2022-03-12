package com.dash.controller

import com.dash.utils.IntegrationTestsUtils
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers.matchesPattern
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
class SteamWidgetControllerTests {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private var jwtToken: String? = null

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

    fun testGetOwnedGamesArguments(): Stream<Arguments> =
        Stream.of(
            arguments(HttpStatus.OK, HttpStatus.OK.value()),
            arguments(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value()),
            arguments(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value()),
            arguments(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value())
        )

    @ParameterizedTest
    @MethodSource("testGetAchievementListArguments")
    fun testGetAchievementList(token: String, statusCode: Int, expectedNumberOfApiRequests: ExpectedCount) {
        mockServer.expect(expectedNumberOfApiRequests, requestTo(matchesPattern(steamApiUrlMatcher)))
            .andExpect(method(HttpMethod.GET))
            .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body("test"))

        given()
            .port(port)
            .header(Header("Authorization", "Bearer $token"))
            .param("appId", 1337)
            .`when`()
            .get("$steamWidgetEndpoint/achievementList")
            .then().log().all()
            .statusCode(statusCode)
            .log().all()

        mockServer.verify()
    }

    fun testGetAchievementListArguments(): Stream<Arguments> {
        return Stream.of(
            arguments(jwtToken, 200, ExpectedCount.once()),
            arguments("WRONG_TOKEN", 401, ExpectedCount.never())
        )
    }
}
