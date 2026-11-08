package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.widget.CreateWidgetRequest
import com.dash.dashboard.adapter.inbound.web.request.widget.UpdateWidgetDataRequest
import com.dash.dashboard.application.port.inbound.WidgetUseCase
import com.dash.dashboard.domain.model.widget.Widget
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/widgets")
class WidgetController(
    private val widgetUseCase: WidgetUseCase
) {
    @GetMapping
    fun getWidgets(
        @RequestParam tabId: Int
    ): List<Widget> = widgetUseCase.getTabWidgets(tabId)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addWidget(
        @RequestBody widgetConfig: CreateWidgetRequest
    ): Widget = widgetUseCase.addWidget(widgetConfig.type, widgetConfig.tabId)

    @PatchMapping("/{widgetId}")
    fun updateWidgetData(
        @PathVariable widgetId: Int,
        @RequestBody updateWidgetDataRequest: UpdateWidgetDataRequest
    ): Widget = widgetUseCase.updateWidgetData(widgetId, updateWidgetDataRequest.data)

    @PatchMapping
    fun updateWidgetsOrder(
        @RequestBody widgetsData: List<Widget>
    ): List<Widget> = widgetUseCase.updateWidgetsOrder(widgetsData)

    @DeleteMapping("/{widgetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteWidget(
        @PathVariable widgetId: Int
    ) {
        widgetUseCase.deleteWidget(widgetId)
    }
}
