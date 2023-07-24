package com.dash.app.controller

import com.common.utils.AbstractIT
import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.app.controller.requests.incidentWidget.IncidentWidgetPayload
import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import com.dash.domain.model.workoutwidget.*
import io.restassured.RestAssured
import io.restassured.RestAssured.given
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.parsing.Parser
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.OffsetDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IncidentWidgetControllerTests : AbstractIT() {

    @LocalServerPort
    private val port: Int = 0
    private lateinit var jwtToken: String
    private val incidentWidgetEndpoint = "/incidentWidget"
    private val widgetId = 55

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
            .param("widgetId", widgetId.toString())
            .get("$incidentWidgetEndpoint/incidentWidgetConfig")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(IncidentDomain::class.java)

        assertNotNull(incidentConfig.lastIncidentDate)

        val updatedIncidentConfig = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .`when`()
            .body(IncidentWidgetPayload(widgetId = widgetId))
            .post("$incidentWidgetEndpoint/startFirstStreak")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(IncidentDomain::class.java)

        assertNotNull(updatedIncidentConfig.lastIncidentDate)

        val streakEndedIncidentConfig = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .`when`()
            .body(IncidentWidgetPayload(widgetId = widgetId))
            .post("$incidentWidgetEndpoint/endStreak")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(IncidentDomain::class.java)

        assertNotNull(streakEndedIncidentConfig.id)
        assertEquals(widgetId, streakEndedIncidentConfig.widgetId)

        val streaks = given()
            .port(port)
            .header(createAuthenticationHeader(jwtToken))
            .contentType(ContentType.JSON)
            .`when`()
            .param("incidentId", incidentConfig.id)
            .get("$incidentWidgetEndpoint/streaks")
            .then().log().all()
            .statusCode(HttpStatus.OK.value())
            .log().all()
            .extract().`as`(object : TypeRef<List<IncidentStreakDomain>>() {})

        assertEquals(1, streaks.size)
        assertNotNull(streaks[0].id)
        assertEquals(incidentConfig.id, streaks[0].incidentId)
        assertEquals(OffsetDateTime.now().dayOfMonth, streaks[0].streakStartDate.dayOfMonth)
        assertEquals(OffsetDateTime.now().dayOfMonth, streaks[0].streakEndDate.dayOfMonth)
    }
}
