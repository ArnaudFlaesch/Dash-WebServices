package com.dash.app.controller

import com.dash.app.controller.requests.CreateWidgetPayload
import com.dash.app.controller.requests.UpdateWidgetDataPayload
import com.dash.domain.model.WidgetDomain
import com.dash.domain.service.WidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/widget")
class WidgetController {

    @Autowired
    private lateinit var widgetService: WidgetService

    @GetMapping("/")
    fun getWidgets(@RequestParam(value = "tabId") tabId: Int): List<WidgetDomain> =
        widgetService.findByTabIdOrderByWidgetOrderAsc(tabId)

    @PostMapping("/addWidget")
    fun addWidget(@RequestBody widgetConfig: CreateWidgetPayload): WidgetDomain = widgetService.addWidget(widgetConfig.type, widgetConfig.tabId)

    @PatchMapping("/updateWidgetData/{widgetId}")
    fun updateWidgetData(
        @PathVariable("widgetId") widgetId: Int,
        @RequestBody updateWidgetDataPayload: UpdateWidgetDataPayload
    ): WidgetDomain =
        widgetService.updateWidget(widgetId, updateWidgetDataPayload.data)

    @PostMapping("/updateWidgetsOrder")
    fun updateWidgetsOrder(@RequestBody widgetEntities: List<WidgetDomain>): List<WidgetDomain> =
        widgetService.updateWidgetsOrder(widgetEntities)

    @DeleteMapping("/deleteWidget")
    fun deleteWidget(@RequestParam(value = "id") id: Int) = widgetService.deleteWidget(id)
}
