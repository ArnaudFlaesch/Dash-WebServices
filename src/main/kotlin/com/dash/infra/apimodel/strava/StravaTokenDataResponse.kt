package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.fasterxml.jackson.annotation.JsonProperty

data class StravaTokenDataResponse(
    @param:JsonProperty("access_token")
    val accessToken: String = "",
    @param:JsonProperty("refresh_token")
    val refreshToken: String = "",
    @param:JsonProperty("expires_at")
    val expiresAt: String = "",
    @param:JsonProperty("athlete")
    val athlete: StravaAthleteResponse = StravaAthleteResponse()
) {
    fun toDomain(): StravaTokenDataDomain =
        StravaTokenDataDomain(
            accessToken = this.accessToken,
            refreshToken = this.refreshToken,
            expiresAt = this.expiresAt,
            athlete = this.athlete.toDomain()
        )
}
