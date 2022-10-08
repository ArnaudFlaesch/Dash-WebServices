package com.dash.app.controller

import com.dash.app.controller.requests.CalendarUrlPayload
import com.dash.domain.model.calendarWidget.CalendarEvent
import com.dash.domain.service.CalendarWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/calendarWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class CalendarWidgetController {

    @Autowired
    private lateinit var calendarWidgetService: CalendarWidgetService

    @PostMapping("/")
    fun getCalendarEvents(@RequestBody calendarUrlPayload: CalendarUrlPayload): List<CalendarEvent>? =
        calendarWidgetService.getIcalDataFromUrl(calendarUrlPayload.calendarUrl)
}
