package com.dash.controller

import com.dash.controller.requests.UpdateWidgetDataPayload
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
    fun getWidgets(@RequestParam(value = "tabId") tabId: Int): List<Widget> =
        widgetService.findByTabIdOrderByWidgetOrderAsc(tabId)

    @PostMapping("/addWidget")
    fun addWidget(@RequestBody widget: Widget): Widget = widgetService.addWidget(widget)

    @PatchMapping("/updateWidgetData/{id}")
    fun updateWidgetData(@PathVariable("id") widgetId: Int, @RequestBody updateWidgetDataPayload: UpdateWidgetDataPayload): Widget =
        widgetService.updateWidget(widgetId, updateWidgetDataPayload)

    @PostMapping("/updateWidgetsOrder")
    fun updateWidgetsOrder(@RequestBody widgets: List<Widget>): List<Widget> = widgetService.updateWidgetsOrder(widgets)

    @DeleteMapping("/deleteWidget")
    fun deleteWidget(@RequestParam(value = "id") id: Int) = widgetService.deleteWidget(id)
}
