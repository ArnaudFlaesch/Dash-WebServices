package com.dash.controller

import com.dash.entity.Widget
import com.dash.service.WidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/widget")
class WidgetController {

    @Autowired
    private lateinit var widgetService: WidgetService

    @GetMapping("/")
    fun getWidgets(@RequestParam(value = "tabId") tabId: Int): List<Widget> {
        return widgetService.findByTabIdOrderByWidgetOrderAsc(tabId)
    }

    @PostMapping("/addWidget")
    fun addWidget(@RequestBody widget: Widget): Widget {
        return widgetService.addWidget(widget)
    }

    @PostMapping("/updateWidgetData")
    fun updateWidgetData(@RequestBody widget: Widget): Widget {
        return widgetService.updateWidget(widget)
    }

    @DeleteMapping("/deleteWidget")
    fun deleteWidget(@RequestParam(value = "id") id: Int) {
        return widgetService.deleteWidget(id)
    }
}
