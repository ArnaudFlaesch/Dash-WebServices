package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.strava.StravaActivity
import com.dash.dashboard.domain.model.strava.StravaAthlete
import com.dash.dashboard.domain.model.strava.StravaTokenData

interface StravaWidgetUseCase {
    fun getToken(apiCode: String): StravaTokenData

    fun getRefreshToken(refreshToken: String): StravaTokenData

    fun getAthleteData(token: String): StravaAthlete

    fun getAthleteActivities(
        token: String,
        pageNumber: Int,
        numberOfActivities: Int
    ): List<StravaActivity>
}
