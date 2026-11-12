package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak

interface IncidentPersistencePort {
    fun findIncidentByWidgetId(widgetId: Int): Incident?

    /** Saves a new incident, or updates an existing one, returning it with its id. */
    fun saveIncident(incident: Incident): Incident

    fun saveIncidentStreak(incidentStreak: IncidentStreak): IncidentStreak

    fun getIncidentStreaks(incidentId: Int): List<IncidentStreak>

    fun isIncidentOwnedBy(
        incidentId: Int,
        userId: Int
    ): Boolean
}
