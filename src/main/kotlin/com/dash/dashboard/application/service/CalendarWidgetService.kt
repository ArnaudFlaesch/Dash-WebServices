package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.CalendarWidgetUseCase
import com.dash.dashboard.application.port.outbound.CalendarPort
import com.dash.dashboard.domain.model.calendar.CalendarEvent
import org.springframework.stereotype.Service

@Service
class CalendarWidgetService(
    private val calendarPort: CalendarPort
) : CalendarWidgetUseCase {
    override fun getCalendarEvents(calendarUrl: String): List<CalendarEvent> =
        calendarPort.getCalendarEvents(calendarUrl)
}
