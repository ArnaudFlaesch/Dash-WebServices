package com.dash.controller

import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin( origins = ["*"])
@RequestMapping("/widget")
class WidgetController {

    @Autowired private lateinit var widgetRepository: WidgetRepository

    @GetMapping("/")
    fun getWidgets(@RequestParam(value = "tabId") tabId: Int) : List<Widget> {
        return (widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId))
    }

    @PostMapping("/addWidget")
    fun addWidget(@RequestBody widget: Widget): Widget {
        return widgetRepository.save(widget)
    }

    @PostMapping("/updateWidget")
    fun updateWidget(@RequestBody widget: Widget): Widget {
        return widgetRepository.save(widget)
    }
}
