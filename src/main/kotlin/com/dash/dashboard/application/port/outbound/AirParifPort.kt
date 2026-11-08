package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.airparif.AirParifColor
import com.dash.dashboard.domain.model.airparif.Prevision

interface AirParifPort {
    fun getPrevisionCommune(communeInseeCode: String): List<Prevision>

    fun getColors(): List<AirParifColor>
}
