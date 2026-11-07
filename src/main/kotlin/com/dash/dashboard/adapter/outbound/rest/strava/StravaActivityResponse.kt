package com.dash.dashboard.adapter.outbound.rest.strava

import com.dash.dashboard.domain.model.strava.StravaActivity
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

private val DEFAULT_LATLNG = listOf(0.0, 0.0)

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class StravaActivityResponse(
    val resourceState: Int = 0,
    val athlete: ActivityAthlete = ActivityAthlete(),
    val name: String? = "",
    val distance: Float = 0f,
    val movingTime: Int = 0,
    val elapsedTime: Int = 0,
    val totalElevationGain: Float = 0f,
    val type: String? = "",
    val workoutType: Int? = 0,
    val id: Long = 0,
    val externalId: String? = "",
    val uploadId: Long? = 0,
    val startDate: String = "",
    val startDateLocal: String = "",
    val timezone: String? = "",
    val utcOffset: Float = 0f,
    val startLatlng: List<Double>? = DEFAULT_LATLNG,
    val endLatlng: List<Double>? = DEFAULT_LATLNG,
    val locationCity: String? = "",
    val locationState: String? = "",
    val startLatitude: Double = 0.0,
    val startLongitude: Double = 0.0,
    val achievementCount: Int = 0,
    val kudosCount: Int = 0,
    val commentCount: Int = 0,
    val athleteCount: Int = 0,
    val photoCount: Int = 0,
    val map: ActivityMap = ActivityMap(),
    val trainer: Boolean = false,
    val commute: Boolean = false,
    val manual: Boolean = false,
    val private: Boolean = false,
    val visibility: String? = "",
    val flagged: Boolean = false,
    val fromAcceptedTag: Boolean = false,
    val uploadIdStr: String? = "",
    val averageSpeed: Float = 0f,
    val maxSpeed: Float = 0f,
    val hasHeartrate: Boolean = false,
    val heartrateOptOut: Boolean = false,
    val displayHideHeartrateOption: Boolean = false,
    val elevHigh: Float = 0f,
    val elevLow: Float = 0f,
    val prCount: Int = 0,
    val totalPhotoCount: Int = 0,
    val hasKudoed: Boolean = false
) {
    fun toDomain(): StravaActivity =
        StravaActivity(
            resourceState = this.resourceState,
            athlete = this.athlete.toDomain(),
            name = this.name.orEmpty(),
            distance = this.distance,
            movingTime = this.movingTime,
            elapsedTime = this.elapsedTime,
            totalElevationGain = this.totalElevationGain,
            type = this.type.orEmpty(),
            workoutType = this.workoutType ?: 0,
            id = this.id,
            externalId = this.externalId,
            uploadId = this.uploadId ?: 0,
            startDate = this.startDate,
            startDateLocal = this.startDateLocal,
            timezone = this.timezone.orEmpty(),
            utcOffset = this.utcOffset,
            startLatlng = this.startLatlng ?: DEFAULT_LATLNG,
            endLatlng = this.endLatlng ?: DEFAULT_LATLNG,
            locationCity = this.locationCity,
            locationState = this.locationState,
            startLatitude = this.startLatitude,
            startLongitude = this.startLongitude,
            achievementCount = this.achievementCount,
            kudosCount = this.kudosCount,
            commentCount = this.commentCount,
            athleteCount = this.athleteCount,
            photoCount = this.photoCount,
            map = this.map.toDomain(),
            trainer = this.trainer,
            commute = this.commute,
            manual = this.manual,
            private = this.private,
            visibility = this.visibility.orEmpty(),
            flagged = this.flagged,
            fromAcceptedTag = this.fromAcceptedTag,
            uploadIdStr = this.uploadIdStr.orEmpty(),
            averageSpeed = this.averageSpeed,
            maxSpeed = this.maxSpeed,
            hasHeartrate = this.hasHeartrate,
            heartrateOptOut = this.heartrateOptOut,
            displayHideHeartrateOption = this.displayHideHeartrateOption,
            elevHigh = this.elevHigh,
            elevLow = this.elevLow,
            prCount = this.prCount,
            totalPhotoCount = this.totalPhotoCount,
            hasKudoed = this.hasKudoed
        )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class ActivityAthlete(
        val id: Long = 0,
        val resourceState: Int = 0
    ) {
        fun toDomain(): StravaActivity.ActivityAthlete =
            StravaActivity.ActivityAthlete(
                id = this.id,
                resourceState = this.resourceState
            )
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class ActivityMap(
        val id: String? = "",
        val summaryPolyline: String? = "",
        val resourceState: Int = 0
    ) {
        fun toDomain(): StravaActivity.ActivityMap =
            StravaActivity.ActivityMap(
                id = this.id.orEmpty(),
                summaryPolyline = this.summaryPolyline.orEmpty(),
                resourceState = this.resourceState
            )
    }
}
