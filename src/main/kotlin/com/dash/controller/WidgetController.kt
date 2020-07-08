package com.dash.controller

import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/widget")
class WidgetController {

    @Autowired
    private lateinit var widgetRepository: WidgetRepository

    @GetMapping("/")
    fun getWidgets(@RequestParam(value = "tabId") tabId: Int): List<Widget> {
        return (widgetRepository.findByTabIdOrderByWidgetOrderAsc(tabId))
    }

    @PostMapping("/addWidget")
    fun addWidget(@RequestBody widget: Widget): Widget {
        widget.widgetOrder = widgetRepository.getNumberOfWidgetsByTab(widget.tab.id) + 1
        return widgetRepository.save(widget)
    }

    @PostMapping("/updateWidgetData")
    fun updateWidgetData(@RequestBody widget: Widget): Widget {
        val oldWidget = widgetRepository.getOne(widget.id)
        oldWidget.data = widget.data
        return widgetRepository.save(oldWidget)
    }

    @DeleteMapping("/deleteWidget")
    fun deleteWidget(@RequestParam(value = "id") id: Int) {
        val widget = widgetRepository.getOne(id)
        return widgetRepository.delete(widget)
    }
}
