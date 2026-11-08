package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.incident.IncidentWidgetRequest
import com.dash.dashboard.application.port.inbound.IncidentWidgetUseCase
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/incidentWidget", produces = [MediaType.APPLICATION_JSON_VALUE])
class IncidentWidgetController(
    private val incidentWidgetUseCase: IncidentWidgetUseCase
) {
    @GetMapping("/incidentWidgetConfig")
    fun getIncidentConfigForWidget(
        @RequestParam widgetId: Int
    ): Incident = incidentWidgetUseCase.getIncidentConfigForWidget(widgetId)

    @PostMapping("/startFirstStreak")
    fun startFirstStreak(
        @RequestBody incidentWidgetRequest: IncidentWidgetRequest
    ): Incident = incidentWidgetUseCase.startFirstStreak(incidentWidgetRequest.widgetId)

    @PostMapping("/endStreak")
    fun endStreak(
        @RequestBody incidentWidgetRequest: IncidentWidgetRequest
    ): Incident = incidentWidgetUseCase.endStreak(incidentWidgetRequest.widgetId)

    @GetMapping("/streaks")
    fun getPastStreaks(
        @RequestParam incidentId: Int
    ): List<IncidentStreak> = incidentWidgetUseCase.getIncidentStreaks(incidentId)
}
