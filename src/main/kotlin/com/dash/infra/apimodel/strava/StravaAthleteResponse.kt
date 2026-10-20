package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming
import java.time.LocalDate

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StravaAthleteResponse(
    val id: Double = 0.0,
    val username: String = "",
    val resourceState: Int = 0,
    val firstname: String = "",
    val lastname: String = "",
    val city: String = "",
    val state: String = "",
    val country: String = "",
    val sex: String = "",
    val premium: Boolean = false,
    val summit: Boolean = false,
    val createdAt: LocalDate = LocalDate.now(),
    val updatedAt: LocalDate = LocalDate.now(),
    val badgeTypeId: Int = 0,
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
