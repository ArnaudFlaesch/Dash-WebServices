package com.dash.infra.rest

import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.apimodel.strava.StravaAthleteResponse
import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Component

@Component
class StravaApiClient(
    private val proxyClient: ProxyClient,
    @Value("\${dash.app.STRAVA_API_URL}")
    private val stravaApiUrl: String,
    @Value("\${dash.app.STRAVA_CLIENT_ID}")
    private val stravaClientId: String,
    @Value("\${dash.app.STRAVA_CLIENT_SECRET}")
    private val stravaClientSecret: String
) {
    companion object {
        private const val TOKEN_ENDPOINT = "/oauth/token"
        private const val ATHLETE_ENDPOINT = "/api/v3/athlete"
        private const val ATHLETE_ACTIVITIES_ENDPOINT = "/api/v3/athlete/activities"
    }

    fun getToken(apiCode: String): StravaTokenDataResponse =
        requestToken("code" to apiCode, "grant_type" to "authorization_code")

    fun getRefreshToken(refreshToken: String): StravaTokenDataResponse =
        requestToken("refresh_token" to refreshToken, "grant_type" to "refresh_token")

    fun getAthleteData(token: String): StravaAthleteResponse =
        proxyClient.get(buildUri(stravaApiUrl, ATHLETE_ENDPOINT), typeReference()) { setHeaders(it, token) }

    fun getAthleteActivities(
        token: String,
        pageNumber: Int,
        numberOfActivities: Int
    ): List<StravaActivityResponse> =
        proxyClient.get(
            buildUri(stravaApiUrl, ATHLETE_ACTIVITIES_ENDPOINT, "page" to pageNumber, "per_page" to numberOfActivities),
            typeReference()
        ) { setHeaders(it, token) }

    private fun requestToken(vararg grantParams: Pair<String, Any>): StravaTokenDataResponse =
        proxyClient.post(
            buildUri(
                stravaApiUrl,
                TOKEN_ENDPOINT,
                "client_id" to stravaClientId,
                "client_secret" to stravaClientSecret,
                *grantParams
            ),
            emptyMap<String, Any>(),
            typeReference()
        )

    private fun setHeaders(
        headers: HttpHeaders,
        token: String
    ) {
        headers.accept = listOf(MediaType.APPLICATION_JSON)
        headers.setBearerAuth(token)
    }
}
