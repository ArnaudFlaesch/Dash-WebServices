package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak

interface IncidentPersistencePort {
    /** Returns the incident configuration of the widget, created if it doesn't exist yet. */
    fun getIncidentConfigForWidget(widgetId: Int): Incident

    fun startStreak(widgetId: Int): Incident

    /** Records the current streak as ended now, and starts a new one. */
    fun endStreak(widgetId: Int): Incident

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreak>

    fun isIncidentOwnedBy(
        incidentId: Int,
        userId: Int
    ): Boolean
}
