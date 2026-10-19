package com.dash.infra.rest

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class AirParifApiClient(
    private val proxyClient: ProxyClient,
    @Value("\${dash.app.AIRPARIF_API_URL}")
    private val airParifApiUrl: String,
    @Value("\${dash.app.AIRPARIF_API_TOKEN}")
    private val airParifToken: String
) {
    companion object {
        private const val AIRPARIF_API_INSEE_ENDPOINT = "/commune"
        private const val AIRPARIF_API_COLORS_ENDPOINT = "/couleurs"
    }

    fun getPrevisionCommune(communeInseeCode: String): Map<String, List<Map<String, String>>> =
        proxyClient.get(
            buildUri(airParifApiUrl, AIRPARIF_API_INSEE_ENDPOINT, "insee" to communeInseeCode),
            typeReference(),
            ::setHeaders
        )

    fun getColors(): Map<String, String> =
        proxyClient.get(buildUri(airParifApiUrl, AIRPARIF_API_COLORS_ENDPOINT), typeReference(), ::setHeaders)

    private fun setHeaders(headers: HttpHeaders) {
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers["X-Api-Key"] = airParifToken
    }
}
