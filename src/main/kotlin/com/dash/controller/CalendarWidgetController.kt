package com.dash.controller

import com.dash.controller.requests.CalendarUrlPayload
import com.dash.service.CalendarWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/calendarWidget")
class CalendarWidgetController {

    @Autowired
    private lateinit var calendarWidgetService: CalendarWidgetService

    @PostMapping("/")
    fun getCalendarEvents(@RequestBody calendarUrlPayload: CalendarUrlPayload): String =
        calendarWidgetService.getIcalDataFromUrl(calendarUrlPayload.calendarUrl)
}
