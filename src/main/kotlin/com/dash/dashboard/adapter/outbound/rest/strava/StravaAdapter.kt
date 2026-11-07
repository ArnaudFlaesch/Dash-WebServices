package com.dash.dashboard.adapter.outbound.rest.strava

import com.dash.dashboard.application.port.outbound.StravaPort
import com.dash.dashboard.domain.model.strava.StravaActivity
import com.dash.dashboard.domain.model.strava.StravaAthlete
import com.dash.dashboard.domain.model.strava.StravaTokenData
import org.springframework.stereotype.Component

@Component
class StravaAdapter(
    private val stravaApiClient: StravaApiClient
) : StravaPort {
    override fun getToken(apiCode: String): StravaTokenData = stravaApiClient.getToken(apiCode).toDomain()

    override fun getRefreshToken(refreshToken: String): StravaTokenData =
        stravaApiClient.getRefreshToken(refreshToken).toDomain()

    override fun getAthleteData(token: String): StravaAthlete = stravaApiClient.getAthleteData(token).toDomain()

    override fun getAthleteActivities(
        token: String,
        pageNumber: Int,
        numberOfActivities: Int
    ): List<StravaActivity> =
        stravaApiClient
            .getAthleteActivities(token, pageNumber, numberOfActivities)
            .map(StravaActivityResponse::toDomain)
}
