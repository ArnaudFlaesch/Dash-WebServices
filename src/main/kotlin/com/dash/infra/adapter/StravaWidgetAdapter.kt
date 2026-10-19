package com.dash.infra.adapter

import com.dash.domain.model.stravaWidget.StravaActivityDomain
import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.rest.StravaApiClient
import org.springframework.stereotype.Component

@Component
class StravaWidgetAdapter(
    private val stravaApiClient: StravaApiClient
) {
    fun getToken(apiCode: String): StravaTokenDataDomain = stravaApiClient.getToken(apiCode).toDomain()

    fun getRefreshToken(refreshToken: String): StravaTokenDataDomain =
        stravaApiClient.getRefreshToken(refreshToken).toDomain()

    fun getAthleteData(token: String): StravaAthleteDomain = stravaApiClient.getAthleteData(token).toDomain()

    fun getAthleteActivities(
        token: String,
        pageNumber: Int,
        numberOfActivities: Int
    ): List<StravaActivityDomain> =
        stravaApiClient
            .getAthleteActivities(token, pageNumber, numberOfActivities)
            .map(StravaActivityResponse::toDomain)
}
