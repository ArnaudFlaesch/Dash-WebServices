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

    fun getToken(apiCode: String): StravaTokenDataDomain {
        val getTokenResponse = stravaApiClient.getToken(apiCode)
        return getTokenResponse.toDomain()
    }

    fun getRefreshToken(refreshToken: String): StravaTokenDataDomain {
        val getRefreshTokenResponse = stravaApiClient.getRefreshToken(refreshToken)
        return getRefreshTokenResponse.toDomain()
    }

    fun getAthleteData(token: String): StravaAthleteDomain {
        val getAthleteResponse = stravaApiClient.getAthleteData(token)
        return getAthleteResponse.toDomain()
    }

    fun getAthleteActivities(token: String, pageNumber: Int, numberOfActivities: Int): List<StravaActivityDomain> {
        val getAthleteActivitiesResponse = stravaApiClient.getAthleteActivities(token, pageNumber, numberOfActivities)
        return getAthleteActivitiesResponse.map(StravaActivityResponse::toDomain)
    }
}
