package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.calendar.CalendarEvent

fun interface CalendarPort {
    /** Fetches the events of the iCal calendar found at the URL. */
    fun getCalendarEvents(calendarUrl: String): List<CalendarEvent>
}
