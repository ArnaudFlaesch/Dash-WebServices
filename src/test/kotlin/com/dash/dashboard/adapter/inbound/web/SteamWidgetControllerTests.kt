package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.shared.domain.model.Page
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import io.restassured.http.Header
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.parsing.Parser
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SteamWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val steamWidgetEndpoint = "/steamWidget"
    private val steamUserIdParam = "1337"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Nested
    @DisplayName("Get player summary tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetPlayerSummaryTests {
        @Test
        fun testGetPlayerSummary() {
            Given {
                port(port)
                    .param("steamUserId", steamUserIdParam)
                    .header(createAuthenticationHeader(jwtToken))
            } When {
                get("$steamWidgetEndpoint/playerData")
            } Then {
                statusCode(200)
            }
        }
    }

    @Nested
    @DisplayName("Get owned games tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetOwnedGamesTests {
        @ParameterizedTest
        @MethodSource("getOwnedGamesArguments")
        fun testGetOwnedGames(
            search: String?,
            expectedNumberOfResults: Int
        ) {
            val ownedGamesData =
                Given {
                    port(port)
                        .param("steamUserId", steamUserIdParam)
                        .header(createAuthenticationHeader(jwtToken))
                } When {
                    param("search", search)
                        .get("$steamWidgetEndpoint/ownedGames")
                } Then {
                    log()
                        .all()
                        .statusCode(HttpStatus.OK.value())
                        .log()
                        .all()
                } Extract {
                    `as`(object : TypeRef<Page<GameInfo>>() {})
                }

            assertEquals(expectedNumberOfResults, ownedGamesData.totalElements.toInt())
        }

        fun getOwnedGamesArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(null, 27),
                Arguments.arguments("Half", 10),
                Arguments.arguments("no results", 0)
            )
    }

    @Nested
    @DisplayName("Get achievements tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAchievementsTests {
        @Test
        fun testGetAchievementList() {
            val actual =
                Given {
                    port(port)
                        .param("steamUserId", steamUserIdParam)
                        .param("appId", 1337)
                        .header(Header("Authorization", "Bearer $jwtToken"))
                } When {
                    get("$steamWidgetEndpoint/achievementList")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(AchievementData::class.java)
                }

            assertEquals(23, actual.playerstats.achievements.size)
        }
    }
}
