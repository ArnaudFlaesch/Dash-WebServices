package com.dash.infra.rest

import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.apimodel.strava.StravaAthleteResponse
import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import java.util.*

@Component
class StravaApiClient(
    private val restClient: RestClient,
    @Value("\${dash.app.STRAVA_API_URL}")
    private val stravaApiUrl: String,
    @Value("\${dash.app.STRAVA_CLIENT_ID}")
    private val stravaClientId: String,
    @Value("\${dash.app.STRAVA_CLIENT_SECRET}")
    private val stravaClientSecret: String
) {
    fun getToken(apiCode: String): StravaTokenDataResponse {
        val url =
            "$stravaApiUrl/oauth/token?client_id=$stravaClientId&client_secret=$stravaClientSecret" +
                "&code=$apiCode&grant_type=authorization_code"
        return restClient.postDataFromProxy(
            url,
            mapOf<String, Any>(),
            StravaTokenDataResponse::class
        )
    }

    fun getRefreshToken(refreshToken: String): StravaTokenDataResponse {
        val url =
            "$stravaApiUrl/oauth/token?client_id=$stravaClientId&client_secret=$stravaClientSecret" +
                "&refresh_token=$refreshToken&grant_type=refresh_token"
        return restClient.postDataFromProxy(
            url,
            mapOf<String, Any>(),
            StravaTokenDataResponse::class
        )
    }

    fun getAthleteData(token: String): StravaAthleteResponse {
        val url = "$stravaApiUrl/api/v3/athlete"
        val httpEntity = HttpEntity<StravaAthleteResponse>(getHeaders(token))
        return restClient.getDataFromProxy(url, StravaAthleteResponse::class, httpEntity)
    }

    fun getAthleteActivities(token: String, pageNumber: Int, numberOfActivities: Int): List<StravaActivityResponse> {
        val url = "$stravaApiUrl/api/v3/athlete/activities?page=$pageNumber&per_page=$numberOfActivities"
        val httpEntity = HttpEntity<List<StravaActivityResponse>>(getHeaders(token))
        val typeReference = object : ParameterizedTypeReference<List<StravaActivityResponse>>() {}
        return restClient.getDataFromProxy(url, typeReference, httpEntity)
    }

    private fun getHeaders(token: String): HttpHeaders {
        val requestHeaders = HttpHeaders()
        requestHeaders.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
        requestHeaders.setBearerAuth(token)
        return requestHeaders
    }
}
