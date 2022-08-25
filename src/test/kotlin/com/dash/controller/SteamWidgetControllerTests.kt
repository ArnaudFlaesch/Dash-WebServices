package com.dash.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.TestEndpointsArguments
import com.dash.model.steamwidget.GameInfoResponse
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.hamcrest.Matchers
import org.hamcrest.Matchers.matchesPattern
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
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
            val getPlayerJsonData = """
            {
              "response": {
                "personaname": personaname,
                "profileurl": profileUrl,
                "avatar": avatar
               }
            }
""".trimIndent()

            mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(steamApiUrlMatcher)))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(getPlayerJsonData))

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

        @Nested
        @DisplayName("Get owned games tests")
        @TestInstance(TestInstance.Lifecycle.PER_CLASS)
        inner class GetOwnedGamesTests {

            @ParameterizedTest
            @MethodSource("getOwnedGamesArguments")
            fun testGetOwnedGames(search: String?, expectedNumberOfResults: Int) {
                mockServer.expect(ExpectedCount.once(), requestTo(matchesPattern(steamApiUrlMatcher)))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(
                        withStatus(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON)
                            .body(getOwnedGamesJsonData)
                    )

                val ownedGamesData = given()
                    .port(port)
                    .header(Header("Authorization", "Bearer $jwtToken"))
                    .`when`()
                    .param("search", search)
                    .get("$steamWidgetEndpoint/ownedGames")
                    .then().log().all()
                    .statusCode(HttpStatus.OK.value())
                    .log().all()
                    .extract().`as`(GameInfoResponse::class.java)

                assertEquals(expectedNumberOfResults, ownedGamesData.response.gameCount)
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

            fun testGetOwnedGamesErrorsCodes(): Stream<Arguments> = TestEndpointsArguments.testForeignApiCodes()

            val getOwnedGamesJsonData = """
            {
              "response": {
                "game_count": 28,
                "games": [
                  {
                    "appid": 220,
                    "name": "Half-Life 2"
                  },
                  {
                    "appid": 340,
                    "name": "Half-Life 2: Lost Coast"
                  },
                  {
                    "appid": 280,
                    "name": "Half-Life: Source"
                  },
                  {
                    "appid": 360,
                    "name": "Half-Life Deathmatch: Source"
                  },
                  {
                    "appid": 320,
                    "name": "Half-Life 2: Deathmatch"
                  },
                  {
                    "appid": 380,
                    "name": "Half-Life 2: Episode One"
                  },
                  {
                    "appid": 420,
                    "name": "Half-Life 2: Episode Two"
                  },
                  {
                    "appid": 2620,
                    "name": "Call of Duty"
                  },
                  {
                    "appid": 2630,
                    "name": "Call of Duty 2"
                  },
                  {
                    "appid": 2641,
                    "name": "Call of Duty 4"
                  },
                  {
                    "appid": 2642,
                    "name": "Call of Duty 5"
                  },
                  {
                    "appid": 2643,
                    "name": "Call of Duty 6"
                  },
                  {
                    "appid": 2644,
                    "name": "Call of Duty 7"
                  },
                  {
                    "appid": 2645,
                    "name": "Call of Duty 8"
                  },
                  {
                    "appid": 2646,
                    "name": "Call of Duty 9"
                  },
                  {
                    "appid": 2647,
                    "name": "Call of Duty 10"
                  },
                  {
                    "appid": 2648,
                    "name": "Call of Duty 11"
                  },
                  {
                    "appid": 2649,
                    "name": "Call of Duty 12"
                  },
                  {
                    "appid": 2650,
                    "name": "Call of Duty 13"
                  },
                  {
                    "appid": 2651,
                    "name": "Call of Duty 14"
                  },
                  {
                    "appid": 2652,
                    "name": "Call of Duty 15"
                  },
                  {
                    "appid": 2653,
                    "name": "Call of Duty 16"
                  },
                  {
                    "appid": 2654,
                    "name": "Call of Duty 17"
                  },
                  {
                    "appid": 2655,
                    "name": "Call of Duty 18"
                  },
                  {
                    "appid": 2656,
                    "name": "Call of Duty 19"
                  },
                  {
                    "appid": 2657,
                    "name": "Call of Duty 20"
                  },
                  {
                    "appid": 2658,
                    "name": "Call of Duty 21"
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
}
