package com.dash.dashboard.adapter.outbound.rest.airparif

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

// Declarative HTTP client, registered in RestClientConfig. Its base URL and default headers come from
// spring.http.serviceclient.airparif in application.yml. A null return value means an empty response body.

/** Previsions by commune INSEE code, each prevision giving a level by pollutant. */
typealias AirParifPrevisionsResponse = Map<String, List<Map<String, String>>>

/** Colors by prevision level name. */
typealias AirParifColorsResponse = Map<String, String>

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface AirParifApi {
    @GetExchange("/commune")
    fun getPrevisionCommune(
        @RequestParam("insee") communeInseeCode: String
    ): AirParifPrevisionsResponse?

    @GetExchange("/couleurs")
    fun getColors(): AirParifColorsResponse?
}
