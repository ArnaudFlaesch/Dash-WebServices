package com.dash.app.controller

import com.dash.app.controller.requests.incidentWidget.IncidentWidgetPayload
import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.domain.model.incidentWidget.IncidentStreakDomain
import com.dash.domain.service.IncidentWidgetService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/incidentWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class IncidentWidgetController(
    private val incidentWidgetService: IncidentWidgetService
) {

    @GetMapping("/incidentWidgetConfig")
    fun getIncidentConfigForWidget(
        @RequestParam(value = "widgetId") widgetId: Int
    ): IncidentDomain = incidentWidgetService.getIncidentConfigForWidget(widgetId)

    @PostMapping("/startFirstStreak")
    fun startFirstStreak(@RequestBody incidentWidgetPayload: IncidentWidgetPayload): IncidentDomain =
        incidentWidgetService.startFirstStreak(incidentWidgetPayload.widgetId)

    @PostMapping("/endStreak")
    fun endStreak(@RequestBody incidentWidgetPayload: IncidentWidgetPayload)
        : IncidentDomain = incidentWidgetService.endStreak(incidentWidgetPayload.widgetId)

    @GetMapping("/streaks")
    fun getPastStreaks(@RequestParam(value = "incidentId") incidentId: Int): List<IncidentStreakDomain> =
        incidentWidgetService.getIncidentStreaks(incidentId)
}
