package com.dash.dashboard.adapter.outbound.rest.strava

import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

// Declarative HTTP client, registered in RestClientConfig. Its base URL and default headers come from
// spring.http.serviceclient.strava in application.yml. A null return value means an empty response body.

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface StravaApi {
    @PostExchange("/oauth/token")
    fun getToken(
        @RequestParam params: Map<String, String>,
        @RequestBody body: Map<String, Any>
    ): StravaTokenDataResponse?

    @GetExchange("/api/v3/athlete")
    fun getAthlete(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String
    ): StravaAthleteResponse?

    @GetExchange("/api/v3/athlete/activities")
    fun getAthleteActivities(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @RequestParam("page") pageNumber: Int,
        @RequestParam("per_page") numberOfActivities: Int
    ): List<StravaActivityResponse>?
}
