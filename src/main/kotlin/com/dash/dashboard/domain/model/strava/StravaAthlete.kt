package com.dash.dashboard.domain.model.strava

import java.time.LocalDate

data class StravaAthlete(
    val id: Long,
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
    val profile: String
)
