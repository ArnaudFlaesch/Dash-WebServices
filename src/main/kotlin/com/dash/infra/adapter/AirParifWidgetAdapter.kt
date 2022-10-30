package com.dash.infra.adapter

import com.dash.domain.mapping.AirParifWidgetMapper
import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.Prevision
import com.dash.infra.rest.AirParifApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import kotlin.collections.LinkedHashMap

@Service
class AirParifWidgetAdapter {

    @Autowired
    private lateinit var airParifApiClient: AirParifApiClient

    @Autowired
    private lateinit var airParifWidgetMapper: AirParifWidgetMapper

    fun getPrevisionCommune(communeInseeCode: String): List<Prevision> {
        val previsionResponse = airParifApiClient.getPrevisionCommune(communeInseeCode) ?: LinkedHashMap()
        return airParifWidgetMapper.previsionsResponseToDomain(communeInseeCode, previsionResponse)
    }

    fun getColors(): List<AirParifColor> {
        val colorsResponse = airParifApiClient.getColors() ?: LinkedHashMap()
        return airParifWidgetMapper.colorsResponseToDomain(colorsResponse)
    }
}
