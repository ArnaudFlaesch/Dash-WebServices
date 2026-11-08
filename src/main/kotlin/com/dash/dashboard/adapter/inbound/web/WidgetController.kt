package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.widget.CreateWidgetRequest
import com.dash.dashboard.adapter.inbound.web.request.widget.UpdateWidgetDataRequest
import com.dash.dashboard.application.port.inbound.WidgetUseCase
import com.dash.dashboard.domain.model.widget.Widget
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/widget")
class WidgetController(
    private val widgetUseCase: WidgetUseCase
) {
    @GetMapping("/")
    fun getWidgets(
        @RequestParam tabId: Int
    ): List<Widget> = widgetUseCase.getTabWidgets(tabId)

    @PostMapping("/addWidget")
    fun addWidget(
        @RequestBody widgetConfig: CreateWidgetRequest
    ): Widget = widgetUseCase.addWidget(widgetConfig.type, widgetConfig.tabId)

    @PatchMapping("/updateWidgetData/{widgetId}")
    fun updateWidgetData(
        @PathVariable widgetId: Int,
        @RequestBody updateWidgetDataRequest: UpdateWidgetDataRequest
    ): Widget = widgetUseCase.updateWidgetData(widgetId, updateWidgetDataRequest.data)

    @PostMapping("/updateWidgetsOrder")
    fun updateWidgetsOrder(
        @RequestBody widgetsData: List<Widget>
    ): List<Widget> = widgetUseCase.updateWidgetsOrder(widgetsData)

    @DeleteMapping("/deleteWidget")
    fun deleteWidget(
        @RequestParam(value = "id") widgetId: Int
    ) {
        widgetUseCase.deleteWidget(widgetId)
    }
}
