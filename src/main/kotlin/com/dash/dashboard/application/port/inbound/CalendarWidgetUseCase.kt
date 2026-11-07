package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.calendar.CalendarEvent

fun interface CalendarWidgetUseCase {
    /** Returns the events of the iCal calendar found at the URL. */
    fun getCalendarEvents(calendarUrl: String): List<CalendarEvent>
}
