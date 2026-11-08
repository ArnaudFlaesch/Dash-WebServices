package com.dash.dashboard.domain.model.strava

data class StravaTokenData(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val athlete: StravaAthlete
)
