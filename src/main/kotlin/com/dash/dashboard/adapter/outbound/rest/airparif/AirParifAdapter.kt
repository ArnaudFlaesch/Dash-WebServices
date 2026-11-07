package com.dash.dashboard.adapter.outbound.rest.airparif

import com.dash.dashboard.application.port.outbound.AirParifPort
import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.Prevision
import org.springframework.stereotype.Component

@Component
class AirParifAdapter(
    private val airParifApiClient: AirParifApiClient
) : AirParifPort {
    override fun getPrevisionCommune(communeInseeCode: String): List<Prevision> =
        previsionsResponseToDomain(communeInseeCode, airParifApiClient.getPrevisionCommune(communeInseeCode))

    override fun getColors(): List<AirParifColor> = colorsResponseToDomain(airParifApiClient.getColors())
}
