package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.IncidentWidgetUseCase
import com.dash.dashboard.application.port.outbound.IncidentPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class IncidentWidgetService(
    private val incidentPersistencePort: IncidentPersistencePort
) : IncidentWidgetUseCase {
    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun getIncidentConfigForWidget(widgetId: Int): Incident =
        incidentPersistencePort.getIncidentConfigForWidget(widgetId)

    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun startFirstStreak(widgetId: Int): Incident = incidentPersistencePort.startStreak(widgetId)

    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun endStreak(widgetId: Int): Incident = incidentPersistencePort.endStreak(widgetId)

    @PreAuthorize(DashboardSecurityConditions.DOES_INCIDENT_BELONG_TO_AUTHENTICATED_USER)
    override fun getIncidentStreaks(incidentId: Int): List<IncidentStreak> =
        incidentPersistencePort.getIncidentStreaks(incidentId)
}
