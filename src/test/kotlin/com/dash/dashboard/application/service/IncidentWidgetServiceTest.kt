package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.outbound.IncidentPersistencePort
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.Clock
import java.time.Instant
import java.time.OffsetDateTime
import java.time.ZoneOffset

class IncidentWidgetServiceTest {
    private val now = OffsetDateTime.of(2026, 3, 1, 8, 0, 0, 0, ZoneOffset.UTC)
    private val streakStart = now.minusDays(30)
    private val widgetId = 3

    private val incidentPersistencePort: IncidentPersistencePort = mock()
    private val incidentWidgetService =
        IncidentWidgetService(incidentPersistencePort, Clock.fixed(Instant.from(now), ZoneOffset.UTC))

    @Test
    fun shouldRecordEndedStreakAndStartNewOne() {
        whenever(
            incidentPersistencePort.findIncidentByWidgetId(widgetId)
        ).thenReturn(Incident(12, streakStart, widgetId))
        whenever(incidentPersistencePort.saveIncident(any())).thenAnswer { invocation -> invocation.arguments[0] }

        val incident = incidentWidgetService.endStreak(widgetId)

        verify(incidentPersistencePort).saveIncidentStreak(IncidentStreak(0, streakStart, now, 12))
        assertEquals(Incident(12, now, widgetId), incident)
    }

    @Test
    fun shouldCreateIncidentStartingNowWhenWidgetHasNone() {
        whenever(
            incidentPersistencePort.saveIncident(Incident(0, now, widgetId))
        ).thenReturn(Incident(1, now, widgetId))

        assertEquals(Incident(1, now, widgetId), incidentWidgetService.getIncidentConfigForWidget(widgetId))
    }
}
