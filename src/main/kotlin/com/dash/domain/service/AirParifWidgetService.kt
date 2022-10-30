package com.dash.domain.service

import com.dash.domain.model.airParif.AirParifColor
import com.dash.domain.model.airParif.Prevision
import com.dash.infra.adapter.AirParifWidgetAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AirParifWidgetService {

    @Autowired
    private lateinit var airParifWidgetAdapter: AirParifWidgetAdapter

    fun getPrevisionCommune(communeInseeCode: String): List<Prevision> =
        airParifWidgetAdapter.getPrevisionCommune(communeInseeCode)

    fun getColors(): List<AirParifColor> = airParifWidgetAdapter.getColors()
}
