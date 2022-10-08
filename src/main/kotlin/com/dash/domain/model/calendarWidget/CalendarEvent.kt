package com.dash.domain.model.calendarWidget

import net.fortuna.ical4j.model.Date

data class CalendarEvent(
    val startDate: Date,
    val endDate: Date,
    val description: String
)
