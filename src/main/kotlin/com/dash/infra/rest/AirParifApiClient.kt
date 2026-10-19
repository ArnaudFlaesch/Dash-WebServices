package com.dash.infra.rest

import com.dash.infra.rest.api.AirParifApi
import org.springframework.stereotype.Component

@Component
class AirParifApiClient(
    private val airParifApi: AirParifApi
) {
    fun getPrevisionCommune(communeInseeCode: String): Map<String, List<Map<String, String>>> =
        airParifApi.getPrevisionCommune(communeInseeCode).orNotFound()

    fun getColors(): Map<String, String> = airParifApi.getColors().orNotFound()
}
