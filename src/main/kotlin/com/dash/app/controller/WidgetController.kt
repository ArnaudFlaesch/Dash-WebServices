package com.dash.app.controller

import com.dash.app.controller.requests.widget.CreateWidgetPayload
import com.dash.app.controller.requests.widget.UpdateWidgetDataPayload
import com.dash.domain.model.WidgetDomain
import com.dash.domain.service.WidgetService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/widget")
class WidgetController(
    private val widgetService: WidgetService
) {

    @GetMapping("/")
    fun getWidgets(@RequestParam(value = "tabId") tabId: Int): List<WidgetDomain> =
        widgetService.findByTabIdOrderByWidgetOrderAsc(tabId)

    @PostMapping("/addWidget")
    fun addWidget(@RequestBody widgetConfig: CreateWidgetPayload): WidgetDomain =
        widgetService.addWidget(widgetConfig.type, widgetConfig.tabId)

    @PatchMapping("/updateWidgetData/{widgetId}")
    fun updateWidgetData(
        @PathVariable("widgetId") widgetId: Int,
        @RequestBody updateWidgetDataPayload: UpdateWidgetDataPayload
    ): WidgetDomain = widgetService.updateWidgetData(widgetId, updateWidgetDataPayload.data)

    @PostMapping("/updateWidgetsOrder")
    fun updateWidgetsOrder(@RequestBody widgetsData: List<WidgetDomain>): List<WidgetDomain> =
        widgetService.updateWidgetsOrder(widgetsData)

    @DeleteMapping("/deleteWidget")
    fun deleteWidget(@RequestParam(value = "id") widgetId: Int) = widgetService.deleteWidget(widgetId)
}
