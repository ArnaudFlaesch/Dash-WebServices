package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.widget.CreateMiniWidgetRequest
import com.dash.dashboard.adapter.inbound.web.request.widget.UpdateWidgetDataRequest
import com.dash.dashboard.application.port.inbound.MiniWidgetUseCase
import com.dash.dashboard.domain.model.widget.MiniWidget
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/mini-widgets")
class MiniWidgetController(
    private val miniWidgetUseCase: MiniWidgetUseCase
) {
    @GetMapping
    fun getWidgets(): List<MiniWidget> = miniWidgetUseCase.getUserMiniWidgets()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addWidget(
        @RequestBody widgetConfig: CreateMiniWidgetRequest
    ): MiniWidget = miniWidgetUseCase.addMiniWidget(widgetConfig.type)

    @PatchMapping("/{widgetId}")
    fun updateWidgetData(
        @PathVariable widgetId: Int,
        @RequestBody updateWidgetDataRequest: UpdateWidgetDataRequest
    ): MiniWidget = miniWidgetUseCase.updateWidgetData(widgetId, updateWidgetDataRequest.data)

    @DeleteMapping("/{widgetId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteMiniWidget(
        @PathVariable widgetId: Int
    ) {
        miniWidgetUseCase.deleteMiniWidget(widgetId)
    }
}
