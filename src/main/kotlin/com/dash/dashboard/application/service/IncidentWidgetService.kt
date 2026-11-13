package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.IncidentWidgetUseCase
import com.dash.dashboard.application.port.outbound.IncidentPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Clock
import java.time.OffsetDateTime

@Service
class IncidentWidgetService(
    private val incidentPersistencePort: IncidentPersistencePort,
    private val clock: Clock
) : IncidentWidgetUseCase {
    @Transactional
    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun getIncidentConfigForWidget(widgetId: Int): Incident =
        onWidgetIncident(widgetId) { incident, _ -> incident }

    @Transactional
    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun startFirstStreak(widgetId: Int): Incident =
        onWidgetIncident(widgetId) { incident, now ->
            incident.restartedAt(now).let(incidentPersistencePort::saveIncident)
        }

    @Transactional
    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun endStreak(widgetId: Int): Incident =
        onWidgetIncident(widgetId) { incident, now ->
            incident.endStreakAt(now).let { (endedStreak, restartedIncident) ->
                incidentPersistencePort.saveIncidentStreak(endedStreak)
                incidentPersistencePort.saveIncident(restartedIncident)
            }
        }

    @PreAuthorize(DashboardSecurityConditions.DOES_INCIDENT_BELONG_TO_AUTHENTICATED_USER)
    override fun getIncidentStreaks(incidentId: Int): List<IncidentStreak> =
        incidentPersistencePort.getIncidentStreaks(incidentId)

    /** Runs [action] on the widget's incident (created if it doesn't exist yet) and the time now. */
    private fun onWidgetIncident(
        widgetId: Int,
        action: (Incident, OffsetDateTime) -> Incident
    ): Incident =
        OffsetDateTime.now(clock).let { now ->
            val incident =
                incidentPersistencePort.findIncidentByWidgetId(widgetId)
                    ?: incidentPersistencePort.saveIncident(Incident.newStartingAt(now, widgetId))
            action(incident, now)
        }
}
