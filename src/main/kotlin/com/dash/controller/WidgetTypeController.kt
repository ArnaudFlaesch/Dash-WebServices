package com.dash.controller

import com.dash.entity.WidgetType
import com.dash.service.WidgetTypeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/widgetTypes")
class WidgetTypeController {

    @Autowired
    private lateinit var widgetTypeService: WidgetTypeService

    @GetMapping("/")
    fun getWidgets(): List<WidgetType> {
        return widgetTypeService.getAllWidgetTypes()
    }
}