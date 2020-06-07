package com.dash.controller

import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/widget")
class WidgetController {

    @Autowired private lateinit var widgetRepository: WidgetRepository

    @GetMapping("/")
    fun getWidgets(@RequestParam(value = "tabId") tabId: Number) : List<Widget> {
        return (widgetRepository.findAll())
    }
}