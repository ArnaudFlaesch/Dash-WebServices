package com.dash.infra.adapter

import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import com.dash.infra.rest.StravaApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class StravaWidgetAdapter {

    @Autowired
    private lateinit var stravaApiClient: StravaApiClient

    fun getToken(apiCode: String): StravaTokenDataDomain {
        val getTokenResponse = stravaApiClient.getToken(apiCode) ?: StravaTokenDataResponse()
        return getTokenResponse.toDomain()
    }

    fun getRefreshToken(refreshToken: String): StravaTokenDataDomain {
        val getRefreshTokenResponse = stravaApiClient.getRefreshToken(refreshToken) ?: StravaTokenDataResponse()
        return getRefreshTokenResponse.toDomain()
    }
}
