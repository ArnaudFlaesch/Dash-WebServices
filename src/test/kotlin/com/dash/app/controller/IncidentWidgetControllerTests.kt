package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.workoutwidget.*
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncidentWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0
    private lateinit var jwtToken: String
    private val incidentWidgetEndpoint = "/incidentWidget"

    @BeforeAll
    fun setup() {
        RestAssured.defaultParser = Parser.JSON
        jwtToken = IntegrationTestsUtils.authenticateAdminRole(port).accessToken
    }

    @Test
    fun getIncidentWidgetConfigTest() {
        val incidentConfig = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .`when`()
            .param("widgetId", "55")
            .get("$incidentWidgetEndpoint/incidentWidgetConfig")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(IncidentDomain::class.java)

        assertEquals("Incident name", incidentConfig.incidentName)
    }
}
