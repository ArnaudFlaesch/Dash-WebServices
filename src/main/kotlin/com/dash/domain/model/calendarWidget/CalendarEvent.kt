package com.dash.domain.model.calendarWidget

import java.time.temporal.Temporal

data class CalendarEvent(
    val startDate: Temporal,
    val endDate: Temporal,
    val description: String
)
