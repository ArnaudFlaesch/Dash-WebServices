package com.dash.infra.rest

import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.apimodel.strava.StravaAthleteResponse
import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import com.dash.infra.rest.api.StravaApi
import org.springframework.stereotype.Component

@Component
class StravaApiClient(
    private val stravaApi: StravaApi,
    apiProperties: ApiProperties
) {
    private val stravaCredentials = apiProperties.strava

    fun getToken(apiCode: String): StravaTokenDataResponse =
        requestToken("code" to apiCode, "grant_type" to "authorization_code")

    fun getRefreshToken(refreshToken: String): StravaTokenDataResponse =
        requestToken("refresh_token" to refreshToken, "grant_type" to "refresh_token")

    fun getAthleteData(token: String): StravaAthleteResponse = stravaApi.getAthlete(bearer(token)).orNotFound()

    fun getAthleteActivities(
        token: String,
        pageNumber: Int,
        numberOfActivities: Int
    ): List<StravaActivityResponse> =
        stravaApi.getAthleteActivities(bearer(token), pageNumber, numberOfActivities).orNotFound()

    private fun requestToken(vararg grantParams: Pair<String, String>): StravaTokenDataResponse =
        stravaApi
            .getToken(
                mapOf(
                    "client_id" to stravaCredentials.clientId,
                    "client_secret" to stravaCredentials.clientSecret,
                    *grantParams
                ),
                emptyMap()
            ).orNotFound()

    private fun bearer(token: String) = "Bearer $token"
}
