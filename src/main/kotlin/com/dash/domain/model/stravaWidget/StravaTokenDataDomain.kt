package com.dash.domain.model.stravaWidget

import java.time.LocalDate

data class StravaTokenDataDomain(
    val accessToken: String,
    val refreshToken: String,
    val expiresAt: String,
    val athlete: StravaAthleteDomain
)

data class StravaAthleteDomain(
    val id: Int,
    val username: String,
    val resourceState: Int,
    val firstname: String,
    val lastname: String,
    val city: String,
    val state: String,
    val country: String,
    val sex: String,
    val premium: Boolean,
    val summit: Boolean,
    val createdAt: LocalDate,
    val updatedAt: LocalDate,
    val badgeTypeId: Int,
    val profileMedium: String,
    val profile: String,
    val friend: Any,
    val follower: Any
)
