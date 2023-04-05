package com.dash.infra.adapter

import com.dash.domain.mapping.AirParifWidgetMapper
import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.Prevision
import com.dash.infra.rest.AirParifApiClient
import org.springframework.stereotype.Component

@Component
class AirParifWidgetAdapter(
    private val airParifApiClient: AirParifApiClient,
    private val airParifWidgetMapper: AirParifWidgetMapper
) {

    fun getPrevisionCommune(communeInseeCode: String): List<Prevision> {
        val previsionResponse = airParifApiClient.getPrevisionCommune(communeInseeCode) ?: LinkedHashMap()
        return airParifWidgetMapper.previsionsResponseToDomain(communeInseeCode, previsionResponse)
    }

    fun getColors(): List<AirParifColor> {
        val colorsResponse = airParifApiClient.getColors() ?: LinkedHashMap()
        return airParifWidgetMapper.colorsResponseToDomain(colorsResponse)
    }
}
