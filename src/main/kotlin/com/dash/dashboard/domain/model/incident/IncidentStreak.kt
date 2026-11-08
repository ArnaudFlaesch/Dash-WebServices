package com.dash.dashboard.domain.model.incident

import java.time.OffsetDateTime

data class IncidentStreak(
    val id: Int,
    val streakStartDate: OffsetDateTime,
    val streakEndDate: OffsetDateTime,
    val incidentId: Int
)
