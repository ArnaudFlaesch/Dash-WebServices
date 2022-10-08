package com.dash.domain.service

import com.dash.domain.mapping.AirParifApiResponseToAirParifDomain
import com.dash.domain.model.airParif.AirParifColors
import com.dash.domain.model.airParif.Prevision
import com.dash.infra.adapter.AirParifWidgetAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AirParifWidgetService {

    @Autowired
    private lateinit var airParifWidgetAdapter: AirParifWidgetAdapter

    @Autowired
    private lateinit var airParifApiResponseToAirParifDomain: AirParifApiResponseToAirParifDomain

    fun getPrevisionCommune(communeInseeCode: String): List<Prevision> {
        val previsionResponse = airParifWidgetAdapter.getPrevisionCommune(communeInseeCode)
        return airParifApiResponseToAirParifDomain.airParifPrevisionsResponseToAirParifDomain(communeInseeCode, previsionResponse)
    }

    fun getColors(): AirParifColors {
        val colorsResponse = airParifWidgetAdapter.getColors()
        return airParifApiResponseToAirParifDomain.airParifColorsResponseToAirParifColorsDomain(colorsResponse)
    }
}
