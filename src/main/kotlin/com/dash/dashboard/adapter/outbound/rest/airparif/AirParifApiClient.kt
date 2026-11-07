package com.dash.dashboard.adapter.outbound.rest.airparif

import com.dash.dashboard.adapter.outbound.rest.orNotFound
import org.springframework.stereotype.Component

@Component
class AirParifApiClient(
    private val airParifApi: AirParifApi
) {
    fun getPrevisionCommune(communeInseeCode: String): Map<String, List<Map<String, String>>> =
        airParifApi.getPrevisionCommune(communeInseeCode).orNotFound()

    fun getColors(): Map<String, String> = airParifApi.getColors().orNotFound()
}
