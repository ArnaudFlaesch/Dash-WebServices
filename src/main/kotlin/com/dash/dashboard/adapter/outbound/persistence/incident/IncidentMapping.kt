package com.dash.dashboard.adapter.outbound.persistence.incident

import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak

fun IncidentEntity.toDomain(): Incident =
    Incident(
        id = id,
        lastIncidentDate = lastIncidentDate,
        widgetId = widget.id
    )

fun IncidentStreakEntity.toDomain(): IncidentStreak =
    IncidentStreak(
        id = id,
        streakStartDate = streakStartDate,
        streakEndDate = streakEndDate,
        incidentId = incident.id
    )
