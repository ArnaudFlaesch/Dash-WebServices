package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import com.dash.utils.IntegrationTestsUtils
import com.dash.utils.IntegrationTestsUtils.createAuthenticationHeader
import com.dash.utils.SqlData
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
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import java.time.OffsetDateTime

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SqlData
class IncidentWidgetControllerTests {
    @LocalServerPort
    private val port: Int = 0
    private lateinit var jwtToken: String
    private val widgetId = 55
    private val incidentWidgetEndpoint = "/widgets/$widgetId/incident"

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
                get(incidentWidgetEndpoint)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(Incident::class.java)
            }

        assertNotNull(incidentConfig.lastIncidentDate)

        val updatedIncidentConfig =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                post(incidentWidgetEndpoint)
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(Incident::class.java)
            }

        assertNotNull(updatedIncidentConfig.lastIncidentDate)

        val streakEndedIncidentConfig =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                post("$incidentWidgetEndpoint/streaks")
            } Then {
                statusCode(HttpStatus.CREATED.value())
            } Extract {
                `as`(Incident::class.java)
            }

        assertNotNull(streakEndedIncidentConfig.id)
        assertEquals(widgetId, streakEndedIncidentConfig.widgetId)

        val streaks =
            Given {
                port(port)
                    .header(createAuthenticationHeader(jwtToken))
                    .contentType(ContentType.JSON)
            } When {
                get("/incidents/${incidentConfig.id}/streaks")
            } Then {
                statusCode(HttpStatus.OK.value())
            } Extract {
                `as`(object : TypeRef<List<IncidentStreak>>() {})
            }

        assertEquals(1, streaks.size)
        assertNotNull(streaks[0].id)
        assertEquals(incidentConfig.id, streaks[0].incidentId)
        assertEquals(OffsetDateTime.now().dayOfMonth, streaks[0].streakStartDate.dayOfMonth)
        assertEquals(OffsetDateTime.now().dayOfMonth, streaks[0].streakEndDate.dayOfMonth)
    }
}
