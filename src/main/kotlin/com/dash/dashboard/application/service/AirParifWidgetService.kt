package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.AirParifWidgetUseCase
import com.dash.dashboard.application.port.outbound.AirParifPort
import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.Prevision
import org.springframework.stereotype.Service

@Service
class AirParifWidgetService(
    private val airParifPort: AirParifPort
) : AirParifWidgetUseCase {
    override fun getPrevisionCommune(communeInseeCode: String): List<Prevision> =
        airParifPort.getPrevisionCommune(communeInseeCode)

    override fun getColors(): List<AirParifColor> = airParifPort.getColors()
}
