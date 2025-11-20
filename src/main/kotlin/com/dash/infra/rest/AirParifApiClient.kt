package com.dash.infra.rest

import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.util.*

@Component
class AirParifApiClient(
    private val restClient: RestClient,
    @param:Value("\${dash.app.AIRPARIF_API_URL}")
    private val airParifApiUrl: String,
    @param:Value("\${dash.app.AIRPARIF_API_TOKEN}")
    private val airParifToken: String
) {
    companion object {
        private const val AIRPARIF_API_INSEE_ENDPOINTS = "/commune"
        private const val AIRPARIF_API_COLORS_ENDPOINTS = "/couleurs"
    }

    fun getPrevisionCommune(communeInseeCode: String): LinkedHashMap<String, List<LinkedHashMap<String, String>>> {
        val url = "$airParifApiUrl$AIRPARIF_API_INSEE_ENDPOINTS?insee=$communeInseeCode"
        val httpEntity =
            HttpEntity<LinkedHashMap<String, List<LinkedHashMap<String, String>>>>(getHeaders())
        val expectedClass =
            object : ParameterizedTypeReference<LinkedHashMap<String, List<LinkedHashMap<String, String>>>>() {}
        return restClient.getDataFromProxy(url, httpEntity, expectedClass)
    }

    fun getColors(): LinkedHashMap<String, String> {
        val url = "$airParifApiUrl$AIRPARIF_API_COLORS_ENDPOINTS"
        val httpEntity = HttpEntity<LinkedHashMap<String, String>>(getHeaders())
        val expectedClass = object : ParameterizedTypeReference<LinkedHashMap<String, String>>() {}
        return restClient.getDataFromProxy(url, httpEntity, expectedClass)
    }

    private fun getHeaders(): HttpHeaders {
        val requestHeaders = HttpHeaders()
        requestHeaders.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
        requestHeaders["X-Api-Key"] = airParifToken
        return requestHeaders
    }
}
