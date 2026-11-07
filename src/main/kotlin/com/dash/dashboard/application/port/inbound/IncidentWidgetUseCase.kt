package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak

interface IncidentWidgetUseCase {
    fun getIncidentConfigForWidget(widgetId: Int): Incident

    fun startFirstStreak(widgetId: Int): Incident

    /** Records the current streak as ended now, and starts a new one. */
    fun endStreak(widgetId: Int): Incident

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreak>
}
