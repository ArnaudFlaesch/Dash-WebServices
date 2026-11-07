package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.StravaWidgetUseCase
import com.dash.dashboard.application.port.outbound.StravaPort
import com.dash.dashboard.domain.model.strava.StravaActivity
import com.dash.dashboard.domain.model.strava.StravaAthlete
import com.dash.dashboard.domain.model.strava.StravaTokenData
import org.springframework.stereotype.Service

@Service
class StravaWidgetService(
    private val stravaPort: StravaPort
) : StravaWidgetUseCase {
    override fun getToken(apiCode: String): StravaTokenData = stravaPort.getToken(apiCode)

    override fun getRefreshToken(refreshToken: String): StravaTokenData = stravaPort.getRefreshToken(refreshToken)

    override fun getAthleteData(token: String): StravaAthlete = stravaPort.getAthleteData(token)

    override fun getAthleteActivities(
        token: String,
        pageNumber: Int,
        numberOfActivities: Int
    ): List<StravaActivity> = stravaPort.getAthleteActivities(token, pageNumber, numberOfActivities)
}
