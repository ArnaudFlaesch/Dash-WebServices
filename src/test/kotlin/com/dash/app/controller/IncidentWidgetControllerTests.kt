package com.dash.app.controller

import com.common.utils.IntegrationTestsUtils
import com.common.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.common.utils.SqlData
import com.dash.app.controller.requests.incidentWidget.IncidentWidgetPayload
import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import io.restassured.RestAssured
import io.restassured.common.mapper.TypeRef
import io.restassured.http.ContentType
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
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
@SqlData
class IncidentWidgetControllerTests {
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
        val incidentConfig =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                param("widgetId", widgetId.toString())
                    .get("$incidentWidgetEndpoint/incidentWidgetConfig")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(IncidentDomain::class.java)
            }

        assertNotNull(incidentConfig.lastIncidentDate)

        val updatedIncidentConfig =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                body(IncidentWidgetPayload(widgetId = widgetId))
                    .post("$incidentWidgetEndpoint/startFirstStreak")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(IncidentDomain::class.java)
            }

        assertNotNull(updatedIncidentConfig.lastIncidentDate)

        val streakEndedIncidentConfig =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                body(IncidentWidgetPayload(widgetId = widgetId))
                    .post("$incidentWidgetEndpoint/endStreak")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(IncidentDomain::class.java)
            }

        assertNotNull(streakEndedIncidentConfig.id)
        assertEquals(widgetId, streakEndedIncidentConfig.widgetId)

        val streaks =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                param("incidentId", incidentConfig.id)
                    .get("$incidentWidgetEndpoint/streaks")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<IncidentStreakDomain>>() {})
            }

        assertEquals(1, streaks.size)
        assertNotNull(streaks[0].id)
        assertEquals(incidentConfig.id, streaks[0].incidentId)
        assertEquals(OffsetDateTime.now().dayOfMonth, streaks[0].streakStartDate.dayOfMonth)
        assertEquals(OffsetDateTime.now().dayOfMonth, streaks[0].streakEndDate.dayOfMonth)
    }
}
