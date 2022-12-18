package com.dash.app.controller

import com.dash.app.controller.requests.widget.CreateMiniWidgetPayload
import com.dash.app.controller.requests.widget.UpdateWidgetDataPayload
import com.dash.domain.model.MiniWidgetDomain
import com.dash.domain.service.MiniWidgetService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/miniWidget")
class MiniWidgetController(
    private val miniWidgetService: MiniWidgetService
) {

    @GetMapping("/")
    fun getWidgets(): List<MiniWidgetDomain> = miniWidgetService.findAuthenticatedUserMiniWidgets()

    @PostMapping("/addMiniWidget")
    fun addWidget(@RequestBody widgetConfig: CreateMiniWidgetPayload): MiniWidgetDomain =
        miniWidgetService.addMiniWidget(widgetConfig.type)

    @PatchMapping("/updateWidgetData/{widgetId}")
    fun updateWidgetData(
        @PathVariable("widgetId") widgetId: Int,
        @RequestBody updateWidgetDataPayload: UpdateWidgetDataPayload
    ): MiniWidgetDomain = miniWidgetService.updateWidgetData(widgetId, updateWidgetDataPayload.data)
}
