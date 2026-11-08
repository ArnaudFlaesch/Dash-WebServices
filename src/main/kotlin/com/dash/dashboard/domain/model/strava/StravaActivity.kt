package com.dash.dashboard.domain.model.strava

data class StravaActivity(
    val resourceState: Int,
    val athlete: ActivityAthlete,
    val name: String,
    val distance: Float,
    val movingTime: Int,
    val elapsedTime: Int,
    val totalElevationGain: Float,
    val type: String,
    val workoutType: Int,
    val id: Long,
    val externalId: String?,
    val uploadId: Long,
    val startDate: String,
    val startDateLocal: String,
    val timezone: String,
    val utcOffset: Float,
    val startLatlng: List<Double>,
    val endLatlng: List<Double>,
    val locationCity: String?,
    val locationState: String?,
    val startLatitude: Double,
    val startLongitude: Double,
    val achievementCount: Int,
    val kudosCount: Int,
    val commentCount: Int,
    val athleteCount: Int,
    val photoCount: Int,
    val map: ActivityMap,
    val trainer: Boolean,
    val commute: Boolean,
    val manual: Boolean,
    val private: Boolean,
    val visibility: String,
    val flagged: Boolean,
    val fromAcceptedTag: Boolean,
    val uploadIdStr: String,
    val averageSpeed: Float,
    val maxSpeed: Float,
    val hasHeartrate: Boolean,
    val heartrateOptOut: Boolean,
    val displayHideHeartrateOption: Boolean,
    val elevHigh: Float,
    val elevLow: Float,
    val prCount: Int,
    val totalPhotoCount: Int,
    val hasKudoed: Boolean
) {
    data class ActivityAthlete(
        val id: Long,
        val resourceState: Int
    )

    data class ActivityMap(
        val id: String,
        val summaryPolyline: String,
        val resourceState: Int
    )
}
