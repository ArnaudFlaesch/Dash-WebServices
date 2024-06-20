package com.dash.domain.model.calendarWidget

import java.time.LocalDate

data class CalendarEvent(
    val startDate: LocalDate,
    val endDate: LocalDate,
    val description: String
)
