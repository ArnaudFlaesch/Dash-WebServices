package com.dash.dashboard.domain.model.incident

import java.time.OffsetDateTime

data class Incident(
    val id: Int,
    val lastIncidentDate: OffsetDateTime,
    val widgetId: Int
)
