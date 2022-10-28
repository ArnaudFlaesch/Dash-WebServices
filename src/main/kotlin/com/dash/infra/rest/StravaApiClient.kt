package com.dash.infra.rest

import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.apimodel.strava.StravaAthleteResponse
import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.util.*

@Component
class StravaApiClient {

    @Autowired
    private lateinit var restClient: RestClient

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Value("\${dash.app.STRAVA_CLIENT_ID}")
    private lateinit var stravaClientId: String

    @Value("\${dash.app.STRAVA_CLIENT_SECRET}")
    private lateinit var stravaClientSecret: String

    companion object {
        private const val stravaTokenUrl = "https://www.strava.com/oauth/token"
        private const val stravaAthleteUrl = "https://www.strava.com/api/v3/athlete"
        private const val stravaActivitiesUrl = "https://www.strava.com/api/v3/athlete/activities?page=1&per_page="
    }

    fun getToken(apiCode: String): StravaTokenDataResponse? {
        val url = "$stravaTokenUrl?client_id=$stravaClientId&client_secret=$stravaClientSecret" +
            "&code=$apiCode&grant_type=authorization_code"
        return restClient.postDataFromProxy(url, mapOf<String, Any>(), StravaTokenDataResponse::class)
    }

    fun getRefreshToken(refreshToken: String): StravaTokenDataResponse? {
        val url = "$stravaTokenUrl?client_id=$stravaClientId&client_secret=$stravaClientSecret" +
            "&refresh_token=$refreshToken&grant_type=refresh_token"
        return restClient.postDataFromProxy(url, mapOf<String, Any>(), StravaTokenDataResponse::class)
    }

    fun getAthleteData(token: String): StravaAthleteResponse? {
        val httpEntity = HttpEntity<StravaAthleteResponse>(getHeaders(token))
        return restClient.getDataFromProxy(stravaAthleteUrl, StravaAthleteResponse::class, httpEntity)
    }

    fun getAthleteActivities(token: String, numberOfActivities: Int): List<StravaActivityResponse>? {
        val httpEntity = HttpEntity<List<StravaActivityResponse>>(getHeaders(token))
        val typeReference = object: ParameterizedTypeReference<List<StravaActivityResponse>>(){}
        return restClient.getDataFromProxy("$stravaActivitiesUrl$numberOfActivities", typeReference, httpEntity)
    }

    private fun getHeaders(token: String): HttpHeaders {
        val requestHeaders = HttpHeaders()
        requestHeaders.accept = Collections.singletonList(MediaType.APPLICATION_JSON)
        requestHeaders.set(HttpHeaders.AUTHORIZATION, "Bearer $token")
        return requestHeaders
    }

}
