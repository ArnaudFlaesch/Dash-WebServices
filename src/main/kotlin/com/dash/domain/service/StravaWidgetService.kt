package com.dash.domain.service

import com.dash.domain.model.stravaWidget.StravaActivityDomain
import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.dash.infra.adapter.StravaWidgetAdapter
import org.springframework.stereotype.Service

@Service
class StravaWidgetService(
    private val stravaWidgetAdapter: StravaWidgetAdapter
) {
    fun getToken(apiCode: String): StravaTokenDataDomain = stravaWidgetAdapter.getToken(apiCode)

    fun getRefreshToken(refreshToken: String): StravaTokenDataDomain = stravaWidgetAdapter.getRefreshToken(refreshToken)

    fun getAthleteData(token: String): StravaAthleteDomain = stravaWidgetAdapter.getAthleteData(token)

    fun getAthleteActivities(token: String, pageNumber: Int, numberOfActivities: Int): List<StravaActivityDomain> =
        stravaWidgetAdapter.getAthleteActivities(
            token,
            pageNumber,
            numberOfActivities
        )
}
