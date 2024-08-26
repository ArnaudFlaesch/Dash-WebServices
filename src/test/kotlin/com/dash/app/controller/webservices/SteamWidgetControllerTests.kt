package com.dash.app.controller.webservices

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.response.Page
import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameInfoDomain
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.Header
import io.restassured.parsing.Parser
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
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
            given()
                .port(port)
                .param("steamUserId", steamUserIdParam)
                .header(createAuthenticationHeader(jwtToken))
                .`when`()
                .get("$steamWidgetEndpoint/playerData")
                .then()
                .statusCode(200)
        }
    }

    @Nested
    @DisplayName("Get owned games tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetOwnedGamesTests {
        @ParameterizedTest
        @MethodSource("getOwnedGamesArguments")
        fun testGetOwnedGames(search: String?, expectedNumberOfResults: Int) {
            val ownedGamesData =
                given()
                    .port(port)
                    .param("steamUserId", steamUserIdParam)
                    .header(createAuthenticationHeader(jwtToken))
                    .`when`()
                    .param("search", search)
                    .get("$steamWidgetEndpoint/ownedGames")
                    .then()
                    .log()
                    .all()
                    .statusCode(HttpStatus.OK.value())
                    .log()
                    .all()
                    .extract()
                    .`as`(object : TypeRef<Page<GameInfoDomain>>() {})

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
                given()
                    .port(port)
                    .param("steamUserId", steamUserIdParam)
                    .param("appId", 1337)
                    .header(Header("Authorization", "Bearer $jwtToken"))
                    .`when`()
                    .get("$steamWidgetEndpoint/achievementList")
                    .then()
                    .log()
                    .all()
                    .statusCode(200)
                    .log()
                    .all()
                    .extract()
                    .`as`(AchievementDataDomain::class.java)

            assertEquals(23, actual.playerstats.achievements.size)
        }
    }
}
