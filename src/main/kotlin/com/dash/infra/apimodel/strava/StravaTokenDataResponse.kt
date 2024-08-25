package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class StravaTokenDataResponse(
    @JsonProperty("access_token")
    val accessToken: String = "",
    @JsonProperty("refresh_token")
    val refreshToken: String = "",
    @JsonProperty("expires_at")
    val expiresAt: String = "",
    @JsonProperty("athlete")
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
