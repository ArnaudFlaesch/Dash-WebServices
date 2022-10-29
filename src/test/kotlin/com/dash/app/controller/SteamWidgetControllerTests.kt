package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.TestEndpointsArguments
import com.dash.domain.model.steamWidget.AchievementDataDomain
import com.dash.domain.model.steamWidget.GameDataDomain
import com.dash.infra.api.response.SteamApiResponse
import io.restassured.RestAssured
import io.restassured.RestAssured.given
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
class SteamWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0

    private lateinit var mockServer: MockRestServiceServer

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private lateinit var jwtToken: String

    @Value("\${dash.app.STEAM_API_URL}")
    private lateinit var steamApiUrl: String

    private val steamWidgetEndpoint = "/steamWidget"
    private val steamUserIdParam = "1337"

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
            val getPlayerJsonData = SteamApiResponse.playerJsonData

            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$steamApiUrl.*")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(getPlayerJsonData))

            given()
                .port(port)
                .param("steamUserId", steamUserIdParam)
                .header(createAuthenticationHeader(jwtToken))
                .`when`()
                .get("$steamWidgetEndpoint/playerData")
                .then().log().all()
                .statusCode(200)
                .log().all()

            mockServer.verify()
        }

        @Nested
        @DisplayName("Get owned games tests")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class GetOwnedGamesTests {

            @ParameterizedTest
            @MethodSource("getOwnedGamesArguments")
            fun testGetOwnedGames(search: String?, expectedNumberOfResults: Int) {
                mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$steamApiUrl.*")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(
                        withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                            .body(getOwnedGamesJsonData)
                    )

                val ownedGamesData = given()
                    .port(port)
                    .param("steamUserId", steamUserIdParam)
                    .header(createAuthenticationHeader(jwtToken))
                    .`when`()
                    .param("search", search)
                    .get("$steamWidgetEndpoint/ownedGames")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .log().all()
                    .extract().`as`(GameDataDomain::class.java)

                assertEquals(expectedNumberOfResults, ownedGamesData.gameCount)
                mockServer.verify()
            }

            fun getOwnedGamesArguments(): Stream<Arguments> =
                Stream.of(
                    Arguments.arguments(null, 28),
                    Arguments.arguments("Half", 7),
                    Arguments.arguments("no results", 0)
                )

            @ParameterizedTest
            @MethodSource("testGetOwnedGamesErrorsCodes")
            fun testGetOwnedGamesErrorsCodes(steamApiStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
                mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$steamApiUrl.*")))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(steamApiStatusCodeResponse).contentType(MediaType.APPLICATION_JSON))

                given()
                    .port(port)
                    .param("steamUserId", steamUserIdParam)
                    .header(createAuthenticationHeader(jwtToken))
                    .`when`()
                    .get("$steamWidgetEndpoint/ownedGames")
                    .then().log().all()
                    .statusCode(expectedStatusCode)
                    .log().all()

                mockServer.verify()
            }

            fun testGetOwnedGamesErrorsCodes(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()

            val getOwnedGamesJsonData = SteamApiResponse.ownedGamesJsonData
        }
    }

    @Nested
    @DisplayName("Get achievements tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAchievementsTests {

        val steamApiResponse = SteamApiResponse.halfLifeTwoEpisodeTwoStatsResponse

        @Test
        fun testGetAchievementList() {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern("$steamApiUrl.*")))
                .andExpect(method(HttpMethod.GET))
                .andRespond(
                    withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON).body(steamApiResponse)
                )

            val actual = given()
                .port(port)
                .param("steamUserId", steamUserIdParam)
                .param("appId", 1337)
                .header(Header("Authorization", "Bearer $jwtToken"))
                .`when`()
                .get("$steamWidgetEndpoint/achievementList")
                .then().log().all()
                .statusCode(200)
                .log().all()
                .extract().`as`(AchievementDataDomain::class.java)

            assertEquals(23, actual.playerstats.achievements.size)

            mockServer.verify()
        }
    }
}
