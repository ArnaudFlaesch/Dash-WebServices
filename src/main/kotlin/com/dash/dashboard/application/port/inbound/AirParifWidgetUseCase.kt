package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.Prevision

interface AirParifWidgetUseCase {
    fun getPrevisionCommune(communeInseeCode: String): List<Prevision>

    fun getColors(): List<AirParifColor>
}
