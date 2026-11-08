package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.IncidentWidgetUseCase
import com.dash.dashboard.domain.model.incident.Incident
import com.dash.dashboard.domain.model.incident.IncidentStreak
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(produces = [MediaType.APPLICATION_JSON_VALUE])
class IncidentWidgetController(
    private val incidentWidgetUseCase: IncidentWidgetUseCase
) {
    @GetMapping("/widgets/{widgetId}/incident")
    fun getIncidentConfigForWidget(
        @PathVariable widgetId: Int
    ): Incident = incidentWidgetUseCase.getIncidentConfigForWidget(widgetId)

    @PostMapping("/widgets/{widgetId}/incident")
    fun startFirstStreak(
        @PathVariable widgetId: Int
    ): Incident = incidentWidgetUseCase.startFirstStreak(widgetId)

    @PostMapping("/widgets/{widgetId}/incident/streaks")
    @ResponseStatus(HttpStatus.CREATED)
    fun endStreak(
        @PathVariable widgetId: Int
    ): Incident = incidentWidgetUseCase.endStreak(widgetId)

    @GetMapping("/incidents/{incidentId}/streaks")
    fun getPastStreaks(
        @PathVariable incidentId: Int
    ): List<IncidentStreak> = incidentWidgetUseCase.getIncidentStreaks(incidentId)
}
