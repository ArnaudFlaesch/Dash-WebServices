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
        airParifApiClient.getPrevisionCommune(communeInseeCode).toDomain(communeInseeCode)

    override fun getColors(): List<AirParifColor> = airParifApiClient.getColors().toDomain()
}
