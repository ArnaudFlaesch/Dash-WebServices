package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.AirParifWidgetUseCase
import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.Prevision
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/air-parif", produces = [MediaType.APPLICATION_JSON_VALUE])
class AirParifWidgetController(
    private val airParifWidgetUseCase: AirParifWidgetUseCase
) {
    @GetMapping("/forecasts")
    fun getPrevisionCommune(
        @RequestParam commune: String
    ): List<Prevision> = airParifWidgetUseCase.getPrevisionCommune(commune)

    @GetMapping("/colors")
    fun getAirParifCouleurs(): List<AirParifColor> = airParifWidgetUseCase.getColors()
}
