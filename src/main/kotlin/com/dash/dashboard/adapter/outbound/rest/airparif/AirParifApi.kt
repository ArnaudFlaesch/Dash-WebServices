package com.dash.dashboard.adapter.outbound.rest.airparif

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

// Declarative HTTP client, registered in RestClientConfig. Its base URL and default headers come from
// spring.http.serviceclient.airparif in application.yml. A null return value means an empty response body.

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface AirParifApi {
    @GetExchange("/commune")
    fun getPrevisionCommune(
        @RequestParam("insee") communeInseeCode: String
    ): Map<String, List<Map<String, String>>>?

    @GetExchange("/couleurs")
    fun getColors(): Map<String, String>?
}
