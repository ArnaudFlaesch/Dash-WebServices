package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StravaTokenDataResponse(
    val accessToken: String? = "",
    val refreshToken: String? = "",
    val expiresAt: String? = "",
    val athlete: StravaAthleteResponse? = StravaAthleteResponse()
) {
    fun toDomain(): StravaTokenDataDomain =
        StravaTokenDataDomain(
            accessToken = this.accessToken.orEmpty(),
            refreshToken = this.refreshToken.orEmpty(),
            expiresAt = this.expiresAt.orEmpty(),
            athlete = (this.athlete ?: StravaAthleteResponse()).toDomain()
        )
}
