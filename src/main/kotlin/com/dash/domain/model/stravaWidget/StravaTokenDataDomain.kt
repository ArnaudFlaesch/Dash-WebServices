package com.dash.domain.model.stravaWidget

data class StravaTokenDataDomain(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val athlete: StravaAthleteDomain
)
