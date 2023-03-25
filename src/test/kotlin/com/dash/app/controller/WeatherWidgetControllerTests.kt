package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.TestEndpointsArguments.testTokenArguments
import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import io.restassured.RestAssured
import io.restassured.RestAssured.given
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
class WeatherWidgetControllerTests : AbstractIT() {

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
        fun testGetWeatherData(token: String, statusCode: Int) {
            given()
                .port(port)
                .header(createAuthenticationHeader(token))
                .param("city", "Paris")
                .`when`()
                .get("$weatherWidgetEndpoint/weather")
                .then().log().all()
                .statusCode(statusCode)
                .log().all()
        }

        fun testGetTokenArguments(): Stream<Arguments> = testTokenArguments(jwtToken)

        @Test
        fun should_get_weather_data() {
            val actual = given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .param("city", "Paris")
                .`when`()
                .get("$weatherWidgetEndpoint/weather")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().`as`(OpenWeatherWeatherDomain::class.java)

            assertEquals("Paris", actual.name)
        }
    }

    @Nested
    @DisplayName("Get forecast tests")
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    inner class GetForecastTests {

        @Test
        fun should_get_forecast_data() {
            val actual = given()
                .port(port)
                .header(createAuthenticationHeader(jwtToken))
                .param("city", "Paris")
                .`when`()
                .get("$weatherWidgetEndpoint/forecast")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .log().all()
                .extract().`as`(OpenWeatherForecastDomain::class.java)

            assertEquals(15, actual.list.size)
        }
    }
}
