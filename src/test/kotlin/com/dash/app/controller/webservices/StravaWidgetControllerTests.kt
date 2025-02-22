package com.dash.app.controller.webservices

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.TestEndpointsArguments
import com.dash.app.controller.requests.stravaWidget.GetStravaRefreshTokenPayload
import com.dash.app.controller.requests.stravaWidget.GetStravaTokenPayload
import com.dash.domain.model.stravaWidget.StravaActivityDomain
import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
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
class StravaWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val stravaWidgetEndpoint = "/stravaWidget"

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
            val getStravaTokenPayload = GetStravaTokenPayload("api_code")

            val result =
                Given {
                    port(port)
                        .contentType(ContentType.JSON)
                        .header(Header("Authorization", "Bearer $jwtToken"))
                } When {
                    body(getStravaTokenPayload)
                        .post("$stravaWidgetEndpoint/getToken")
                } Then {
                    statusCode(HttpStatus.OK.value())
                } Extract {
                    `as`(StravaTokenDataDomain::class.java)
                }

            assertEquals("Paris", result.athlete.city)
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
            val getStravaRefreshTokenPayload = GetStravaRefreshTokenPayload("refresh_token")

            Given {
                port(port)
                    .contentType(ContentType.JSON)
                    .header(createAuthenticationHeader(token))
            } When {
                body(getStravaRefreshTokenPayload)
                    .post("$stravaWidgetEndpoint/getRefreshToken")
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
                    get("$stravaWidgetEndpoint/getAthleteData")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(StravaAthleteDomain::class.java)
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
                    get("$stravaWidgetEndpoint/getAthleteActivities")
                } Then {
                    log()
                        .all()
                        .statusCode(200)
                        .log()
                        .all()
                } Extract {
                    `as`(object : TypeRef<List<StravaActivityDomain>>() {})
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
