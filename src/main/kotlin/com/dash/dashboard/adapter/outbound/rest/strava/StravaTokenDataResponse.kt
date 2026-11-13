package com.dash.dashboard.adapter.outbound.rest.strava

import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StravaTokenDataResponse(
    val accessToken: String? = "",
    val refreshToken: String? = "",
    val expiresAt: String? = "",
    val athlete: StravaAthleteResponse? = StravaAthleteResponse()
)
