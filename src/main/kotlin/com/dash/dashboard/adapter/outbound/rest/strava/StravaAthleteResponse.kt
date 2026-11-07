package com.dash.dashboard.adapter.outbound.rest.strava

import com.dash.dashboard.domain.model.strava.StravaAthlete
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming
import java.time.LocalDate

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StravaAthleteResponse(
    val id: Long = 0,
    val username: String? = "",
    val resourceState: Int = 0,
    val firstname: String? = "",
    val lastname: String? = "",
    val city: String? = "",
    val state: String? = "",
    val country: String? = "",
    val sex: String? = "",
    val premium: Boolean = false,
    val summit: Boolean = false,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val badgeTypeId: Int = 0,
    val profileMedium: String? = "",
    val profile: String? = ""
) {
    fun toDomain(): StravaAthlete =
        StravaAthlete(
            id = this.id,
            username = this.username.orEmpty(),
            resourceState = this.resourceState,
            firstname = this.firstname.orEmpty(),
            lastname = this.lastname.orEmpty(),
            city = this.city.orEmpty(),
            state = this.state.orEmpty(),
            country = this.country.orEmpty(),
            sex = this.sex.orEmpty(),
            premium = this.premium,
            summit = this.summit,
            createdAt = this.createdAt,
            updatedAt = this.updatedAt,
            badgeTypeId = this.badgeTypeId,
            profileMedium = this.profileMedium.orEmpty(),
            profile = this.profile.orEmpty()
        )
}
