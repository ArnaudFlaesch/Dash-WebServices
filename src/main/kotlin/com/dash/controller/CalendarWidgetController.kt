package com.dash.controller

import com.dash.controller.requests.CalendarUrlPayload
import com.dash.model.CalendarEvent
import com.dash.service.CalendarWidgetService
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
