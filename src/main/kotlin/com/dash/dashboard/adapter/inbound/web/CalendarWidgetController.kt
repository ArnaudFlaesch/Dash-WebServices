package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.calendar.CalendarUrlRequest
import com.dash.dashboard.application.port.inbound.CalendarWidgetUseCase
import com.dash.dashboard.domain.model.calendar.CalendarEvent
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/calendar", produces = [MediaType.APPLICATION_JSON_VALUE])
class CalendarWidgetController(
    private val calendarWidgetUseCase: CalendarWidgetUseCase
) {
    // POST plutot que GET : les URL iCal contiennent souvent un jeton secret
    // qui ne doit apparaitre ni dans la query string ni dans les logs.
    @PostMapping("/events")
    fun getCalendarEvents(
        @RequestBody calendarUrlRequest: CalendarUrlRequest
    ): List<CalendarEvent> = calendarWidgetUseCase.getCalendarEvents(calendarUrlRequest.calendarUrl)
}
