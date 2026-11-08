package com.dash.dashboard.adapter.outbound.persistence.incident

import com.dash.dashboard.adapter.outbound.persistence.widget.WidgetEntity
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import jakarta.persistence.*
import java.time.OffsetDateTime

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
