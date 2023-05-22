package com.dash.domain.model.stravaWidget

data class StravaActivityDomain(
    val resourceState: Int,
    val athlete: ActivityAthleteDomain,
    val name: String,
    val distance: Float,
    val movingTime: Int,
    val elapsedTime: Int,
    val totalElevationGain: Float,
    val type: String,
    val workoutType: Int,
    val id: Double,
    val externalId: String,
    val uploadId: Double,
    val startDate: String,
    val startDateLocal: String,
    val timezone: String,
    val utcOffset: Float,
    val startLatlng: IntArray,
    val endLatlng: IntArray,
    val locationCity: String?,
    val locationState: String?,
    val locationCountry: String,
    val startLatitude: Int,
    val startLongitude: Int,
    val achievementCount: Int,
    val kudosCount: Int,
    val commentCount: Int,
    val athleteCount: Int,
    val photoCount: Int,
    val map: ActivityMapDomain,
    val trainer: Boolean,
    val commute: Boolean,
    val manual: Boolean,
    val private: Boolean,
    val visibility: String,
    val flagged: Boolean,
    val fromAcceptedTag: Boolean,
    val uploadIdStr: Double,
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
    data class ActivityAthleteDomain(
        val id: Int,
        val resourceState: Int
    )

    data class ActivityMapDomain(
        val id: String,
        val summaryPolyline: String,
        val resourceState: Int
    )
}
