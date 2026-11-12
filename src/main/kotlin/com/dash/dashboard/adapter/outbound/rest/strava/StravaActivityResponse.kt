package com.dash.dashboard.adapter.outbound.rest.strava

import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

internal val DEFAULT_LATLNG = listOf(0.0, 0.0)

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
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class ActivityAthlete(
        val id: Long = 0,
        val resourceState: Int = 0
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class ActivityMap(
        val id: String? = "",
        val summaryPolyline: String? = "",
        val resourceState: Int = 0
    )
}
