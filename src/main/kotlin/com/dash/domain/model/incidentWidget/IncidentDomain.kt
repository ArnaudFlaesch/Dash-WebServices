package com.dash.domain.model.incidentWidget

import java.time.OffsetDateTime

data class IncidentDomain(
    val id: Int,
    val lastIncidentDate: OffsetDateTime,
    val widgetId: Int
)
