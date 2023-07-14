package com.dash.domain.model.incidentWidget

import java.time.OffsetDateTime

data class IncidentStreakDomain(
    val id: Int,
    val streakStartDate: OffsetDateTime,
    val streakEndDate: OffsetDateTime,
    val incidentId: Int
)
