package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.strava.GetStravaRefreshTokenRequest
import com.dash.dashboard.adapter.inbound.web.request.strava.GetStravaTokenRequest
import com.dash.dashboard.domain.model.strava.StravaActivity
import com.dash.dashboard.domain.model.strava.StravaAthlete
import com.dash.dashboard.domain.model.strava.StravaTokenData
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.utils.TestEndpointsArguments
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
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
import java.time.LocalDate
import java.util.stream.Stream

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class StravaWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val stravaWidgetEndpoint = "/strava"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Nested
    @DisplayName("Get token tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetTokenTests {
        @Test
        fun shouldGetToken() {
            val getStravaTokenRequest = GetStravaTokenRequest("api_code")

            val result =
                Given {
                    port(port)
                        .contentType(ContentType.JSON)
                        .header(Header("Authorization", "Bearer $jwtToken"))
                } When {
                    body(getStravaTokenRequest)
                        .post("$stravaWidgetEndpoint/token")
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(StravaTokenData::class.java)
                }

            assertEquals("TOKEN", result.accessToken)
            assertEquals("REFRESH_TOKEN", result.refreshToken)
            assertEquals("1644882561", result.expiresAt)
            assertEquals("Paris", result.athlete.city)
            assertEquals(LocalDate.of(2017, 9, 28), result.athlete.createdAt)
            assertEquals("jpg", result.athlete.profileMedium)
        }
    }

    @Nested
    @DisplayName("Get refresh token tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetRefreshTokenTests {
        @ParameterizedTest
        @MethodSource("testGetRefreshTokenArguments")
        fun shouldGetRefreshToken(
            token: String,
            statusCode: Int
        ) {
            val getStravaRefreshTokenRequest = GetStravaRefreshTokenRequest("refresh_token")

            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(token))
            } When {
                body(getStravaRefreshTokenRequest)
                    .post("$stravaWidgetEndpoint/token/refresh")
            } Then {
                statusCode(statusCode)
            }
        }

        fun testGetRefreshTokenArguments(): Stream<Arguments> = TestEndpointsArguments.testTokenArguments(jwtToken)
    }

    @Nested
    @DisplayName("Get athlete data tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAthleteDataTests {
        @Test
        fun shouldGetAthleteData() {
            val actual =
                Given {
                    port(port)
                        .contentType(ContentType.JSON)
                        .header(createAuthenticationHeader(jwtToken))
                        .param("token", "VALID_TOKEN")
                } When {
                    get("$stravaWidgetEndpoint/athlete")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(StravaAthlete::class.java)
                }

            assertEquals("Paris", actual.city)
        }
    }

    @Nested
    @DisplayName("Get athlete activities tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetAthleteActivitiesTests {
        @ParameterizedTest
        @MethodSource("getActivitiesArguments")
        fun shouldGetAthleteActivities(
            params: Map<String, Any>,
            expectedSize: Int
        ) {
            val actual =
                Given {
                    port(port)
                        .contentType(ContentType.JSON)
                        .header(createAuthenticationHeader(jwtToken))
                        .params(params)
                } When {
                    get("$stravaWidgetEndpoint/athlete/activities")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(object : TypeRef<List<StravaActivity>>() {})
                }

            assertEquals(expectedSize, actual.size)
        }

        fun getActivitiesArguments(): Stream<Arguments> =
            Stream.of(
                Arguments.arguments(mapOf("token" to "VALID_TOKEN"), 6),
                Arguments.arguments(mapOf("token" to "VALID_TOKEN", "numberOfActivities" to 25), 6),
                Arguments.arguments(mapOf("token" to "VALID_TOKEN", "numberOfActivities" to 10, "pageNumber" to 0), 6)
            )
    }
}
