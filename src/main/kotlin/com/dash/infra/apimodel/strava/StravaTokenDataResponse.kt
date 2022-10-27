package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

data class StravaTokenDataResponse(
    @JsonProperty("access_token")
    val accessToken: String = "",
    @JsonProperty("refresh_token")
    val refreshToken: String = "",
    @JsonProperty("expires_at")
    val expiresAt: String = "",
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

data class StravaAthleteResponse(
    val id: Int = 0,
    val username: String = "",
    @JsonProperty("resource_state")
    val resourceState: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val sex: String = "",
    val premium: Boolean = false,
    val summit: Boolean = false,
    @JsonProperty("created_at")
    val createdAt: LocalDate = LocalDate.now(),
    @JsonProperty("updated_at")
    val updatedAt: LocalDate = LocalDate.now(),
    @JsonProperty("badge_type_id")
    val badgeTypeId: Int = 0,
    @JsonProperty("profile_medium")
    val profileMedium: String = "",
    val profile: String = "",
    val friend: Any = "",
    val follower: Any = ""
) {
    fun toDomain(): StravaAthleteDomain =
        StravaAthleteDomain(
            id = this.id,
            username = this.username,
            resourceState = this.resourceState,
            firstname = this.firstname,
            lastname = this.lastname,
            city = this.city,
            state = this.state,
            country = this.country,
            sex = this.sex,
            premium = this.premium,
            summit = this.summit,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            badgeTypeId = this.badgeTypeId,
            profileMedium = this.profileMedium,
            profile = this.profile,
            friend = this.friend,
            follower = this.follower
        )
}
