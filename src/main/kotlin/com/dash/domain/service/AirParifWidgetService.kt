package com.dash.domain.service

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.Prevision
import com.dash.infra.adapter.AirParifWidgetAdapter
import org.springframework.stereotype.Service

@Service
class AirParifWidgetService(
    private val airParifWidgetAdapter: AirParifWidgetAdapter
) {
    fun getPrevisionCommune(communeInseeCode: String): List<Prevision> = airParifWidgetAdapter.getPrevisionCommune(communeInseeCode)

    fun getColors(): List<AirParifColor> = airParifWidgetAdapter.getColors()
}
