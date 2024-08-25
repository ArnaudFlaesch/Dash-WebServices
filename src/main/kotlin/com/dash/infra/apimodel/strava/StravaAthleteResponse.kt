package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class StravaAthleteResponse(
    val id: Double = 0.0,
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
    val profile: String = ""
) {
    fun toDomain(): StravaAthleteDomain =
        StravaAthleteDomain(
            id = this.id.toInt(),
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
            profile = this.profile
        )
}
