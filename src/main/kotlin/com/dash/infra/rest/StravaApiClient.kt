package com.dash.infra.rest

import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class StravaApiClient {

    @Autowired
    private lateinit var restClient: RestClient

    @Value("\${dash.app.STRAVA_CLIENT_ID}")
    private lateinit var stravaClientId: String

    @Value("\${dash.app.STRAVA_CLIENT_SECRET}")
    private lateinit var stravaClientSecret: String

    companion object {
        private const val stravaTokenUrl = "https://www.strava.com/oauth/token"
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
}
