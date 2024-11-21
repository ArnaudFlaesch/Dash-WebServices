package com.dash.app.controller.webservices

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.TestEndpointsArguments.testTokenArguments
import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import io.restassured.RestAssured
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
class WeatherWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0

    private lateinit var jwtToken: String

    private val weatherWidgetEndpoint = "/weatherWidget"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Nested
    @DisplayName("Get weather tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetWeatherTests {
        @ParameterizedTest
        @MethodSource("testGetTokenArguments")
        fun testGetWeatherData(
            token: String,
            statusCode: Int
        ) {
            Given {
                port(port)
                    .header(createAuthenticationHeader(token))
                    .param("city", "Paris")
            } When {
                get("$weatherWidgetEndpoint/weather")
            } Then {
                statusCode(statusCode)
            }
        }

        fun testGetTokenArguments(): Stream<Arguments> = testTokenArguments(jwtToken)

        @Test
        fun shouldGetWeatherData() {
            val actual =
                Given {
                    port(port)
                        .header(createAuthenticationHeader(jwtToken))
                        .param("city", "Paris")
                } When {
                    get("$weatherWidgetEndpoint/weather")
                } Then {
                    log()
                        .all()
                        .statusCode(HttpStatus.OK.value())
                        .log()
                        .all()
                } Extract {
                    `as`(OpenWeatherWeatherDomain::class.java)
                }

            assertEquals("Paris", actual.name)
        }
    }

    @Nested
    @DisplayName("Get forecast tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetForecastTests {
        @Test
        fun shouldGetForecastData() {
            val actual =
                Given {
                    port(port)
                        .header(createAuthenticationHeader(jwtToken))
                        .param("city", "Paris")
                } When {
                    get("$weatherWidgetEndpoint/forecast")
                } Then {
                    log()
                        .all()
                        .statusCode(HttpStatus.OK.value())
                        .log()
                        .all()
                } Extract {
                    `as`(OpenWeatherForecastDomain::class.java)
                }

            assertEquals(15, actual.list.size)
        }
    }
}
