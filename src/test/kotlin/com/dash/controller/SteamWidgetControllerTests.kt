package com.dash.controller

import com.cashmanager.model.ImportData
import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.TestEndpointsArguments
import com.dash.model.GameInfo
import com.dash.model.GameInfoResponse
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.Assertions.assertEquals
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

        @Test
        fun testGetOwnedGames() {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(steamApiUrlMatcher)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                    .body(getOwnedGamesJsonData))

            given()
                .port(port)
                .header(Header("Authorization", "Bearer $jwtToken"))
                .`when`()
                .get("$steamWidgetEndpoint/ownedGames")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .log().all()

            mockServer.verify()
        }

        @ParameterizedTest
        @MethodSource("testGetOwnedGamesErrorsCodes")
        fun testGetOwnedGamesErrorsCodes(steamApiStatusCodeResponse: HttpStatus, expectedStatusCode: Int) {
            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(steamApiUrlMatcher)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(steamApiStatusCodeResponse).contentType(MediaType.APPLICATION_JSON))

            val ownedGamesData = given()
                .port(port)
                .header(Header("Authorization", "Bearer $jwtToken"))
                .`when`()
                .get("$steamWidgetEndpoint/ownedGames")
                .then().log().all()
                .statusCode(expectedStatusCode)
                .log().all()
                .extract().`as`(GameInfoResponse::class.java)

            assertEquals(20, ownedGamesData.response.gameCount)
            mockServer.verify()
        }

        fun testGetOwnedGamesErrorsCodes(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()

        val getOwnedGamesJsonData = """
            {
              "response": {
                "game_count": 10,
                "games": [
                  {
                    "appid": 220,
                    "name": "Half-Life 2",
                    "playtime_forever": 2480,
                    "img_icon_url": "fcfb366051782b8ebf2aa297f3b746395858cb62",
                    "img_logo_url": "e4ad9cf1b7dc8475c1118625daf9abd4bdcbcad0",
                    "has_community_visible_stats": true,
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 340,
                    "name": "Half-Life 2: Lost Coast",
                    "playtime_forever": 32,
                    "img_icon_url": "795e85364189511f4990861b578084deef086cb1",
                    "img_logo_url": "867cce5c4f37d5ed4aeffb57c60e220ddffe4134",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 280,
                    "name": "Half-Life: Source",
                    "playtime_forever": 774,
                    "img_icon_url": "b4f572a6cc5a6a84ae84634c31414b9123d2f26b",
                    "img_logo_url": "a612dd944b768e55389140298dcfda2165db8ced",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 360,
                    "name": "Half-Life Deathmatch: Source",
                    "playtime_forever": 26,
                    "img_icon_url": "40b8a62efff5a9ab356e5c56f5c8b0532c8e1aa3",
                    "img_logo_url": "9a5b7119d4e8977fffcd370d3c24036be7cee904",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 320,
                    "name": "Half-Life 2: Deathmatch",
                    "playtime_forever": 15,
                    "img_icon_url": "795e85364189511f4990861b578084deef086cb1",
                    "img_logo_url": "6dd9f66771300f2252d411e50739a1ceae9e5b30",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 380,
                    "name": "Half-Life 2: Episode One",
                    "playtime_forever": 442,
                    "img_icon_url": "795e85364189511f4990861b578084deef086cb1",
                    "img_logo_url": "b5a666a961d8b39896887abbed3b78c2b837c238",
                    "has_community_visible_stats": true,
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 420,
                    "name": "Half-Life 2: Episode Two",
                    "playtime_forever": 804,
                    "img_icon_url": "795e85364189511f4990861b578084deef086cb1",
                    "img_logo_url": "553e6a2e7a469dcbaada729baa1f5fd7764668df",
                    "has_community_visible_stats": true,
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 2620,
                    "name": "Call of Duty",
                    "playtime_forever": 381,
                    "img_icon_url": "b4ce47894dee6ec21b0ad96cfb2b8c0036098e39",
                    "img_logo_url": "e7e4b54c87592bba8978424ba3258b215c2497cd",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 2630,
                    "name": "Call of Duty 2",
                    "playtime_forever": 410,
                    "img_icon_url": "2e455daa8702a3cb9da8733232ec330a06936df3",
                    "img_logo_url": "1341359c59254fad618dfa599f7c5d7e82f3aade",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  },
                  {
                    "appid": 2640,
                    "name": "Call of Duty: United Offensive",
                    "playtime_forever": 363,
                    "img_icon_url": "e5fbc61fce98c303a9c49dc988163499b183f037",
                    "img_logo_url": "658a03d797260730790010c1d14ca060ac143a8f",
                    "playtime_windows_forever": 0,
                    "playtime_mac_forever": 0,
                    "playtime_linux_forever": 0
                  }
                ]
              }
            }
        """.trimIndent()
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
