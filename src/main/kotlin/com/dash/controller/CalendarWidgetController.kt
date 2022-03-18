package com.dash.controller

import com.dash.service.CalendarWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/calendarWidget")
class CalendarWidgetController {

    @Autowired
    private lateinit var calendarWidgetService: CalendarWidgetService

    @GetMapping("/")
    fun getCalendarEvents(@RequestParam(value = "calendarUrl") calendarUrl: String): String =
        calendarWidgetService.getIcalDataFromUrl(calendarUrl)
}
