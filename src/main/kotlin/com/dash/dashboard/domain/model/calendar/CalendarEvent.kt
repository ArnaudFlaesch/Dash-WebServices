package com.dash.dashboard.domain.model.calendar

import java.time.temporal.Temporal

data class CalendarEvent(
    val startDate: Temporal,
    val endDate: Temporal,
    val description: String
)
