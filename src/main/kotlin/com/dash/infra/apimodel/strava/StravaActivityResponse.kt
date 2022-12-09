package com.dash.infra.apimodel.strava

import com.dash.domain.model.stravaWidget.StravaActivityDomain
import com.fasterxml.jackson.annotation.JsonProperty

data class StravaActivityResponse(
    @JsonProperty("resource_state")
    val resourceState: Int = 0,
    val athlete: ActivityAthlete = ActivityAthlete(),
    val name: String = "",
    val distance: Float = 0f,
    @JsonProperty("moving_time")
    val movingTime: Int = 0,
    @JsonProperty("elapsed_time")
    val elapsedTime: Int = 0,
    @JsonProperty("total_elevation_gain")
    val totalElevationGain: Float = 0f,
    val type: String = "",
    @JsonProperty("workout_type")
    val workoutType: Int = 0,
    val id: Double = 0.0,
    @JsonProperty("external_id")
    val externalId: String = "",
    @JsonProperty("upload_id")
    val uploadId: Double = 0.0,
    @JsonProperty("start_date")
    val startDate: String = "",
    @JsonProperty("start_date_local")
    val startDateLocal: String = "",
    val timezone: String = "",
    @JsonProperty("utc_offset")
    val utcOffset: Float = 0f,
    @JsonProperty("start_latlng")
    val startLatlng: IntArray = IntArray(2),
    @JsonProperty("end_latlng")
    val endLatlng: IntArray = IntArray(2),
    @JsonProperty("location_city")
    val locationCity: String? = "",
    @JsonProperty("location_state")
    val locationState: String? = "",
    @JsonProperty("location_country")
    val locationCountry: String = "",
    @JsonProperty("start_latitude")
    val startLatitude: Int = 0,
    @JsonProperty("start_longitude")
    val startLongitude: Int = 0,
    @JsonProperty("achievement_count")
    val achievementCount: Int = 0,
    @JsonProperty("kudos_count")
    val kudosCount: Int = 0,
    @JsonProperty("comment_count")
    val commentCount: Int = 0,
    @JsonProperty("athlete_count")
    val athleteCount: Int = 0,
    @JsonProperty("photo_count")
    val photoCount: Int = 0,
    val map: ActivityMap = ActivityMap(),
    val trainer: Boolean = false,
    val commute: Boolean = false,
    val manual: Boolean = false,
    val private: Boolean = false,
    val visibility: String = "",
    val flagged: Boolean = false,
    @JsonProperty("gear_id")
    val gearId: String = "",
    @JsonProperty("from_accepted_tag")
    val fromAcceptedTag: Boolean = false,
    @JsonProperty("upload_id_str")
    val uploadIdStr: Double = 0.0,
    @JsonProperty("average_speed")
    val averageSpeed: Float = 0f,
    @JsonProperty("max_speed")
    val maxSpeed: Float = 0f,
    @JsonProperty("has_heartrate")
    val hasHeartrate: Boolean = false,
    @JsonProperty("heartrate_opt_out")
    val heartrateOptOut: Boolean = false,
    @JsonProperty("display_hide_heartrate_option")
    val displayHideHeartrateOption: Boolean = false,
    @JsonProperty("elev_high")
    val elevHigh: Float = 0f,
    @JsonProperty("elev_low")
    val elevLow: Float = 0f,
    @JsonProperty("pr_count")
    val prCount: Int = 0,
    @JsonProperty("total_photo_count")
    val totalPhotoCount: Int = 0,
    @JsonProperty("has_kudoed")
    val hasKudoed: Boolean = false
) {
    fun toDomain(): StravaActivityDomain =
        StravaActivityDomain(
            resourceState = this.resourceState,
            athlete = this.athlete.toDomain(),
            name = this.name,
            distance = this.distance,
            movingTime = this.movingTime,
            elapsedTime = this.elapsedTime,
            totalElevationGain = this.totalElevationGain,
            type = this.type,
            workoutType = this.workoutType,
            id = this.id,
            externalId = this.externalId,
            uploadId = this.uploadId,
            startDate = this.startDate,
            startDateLocal = this.startDateLocal,
            timezone = this.timezone,
            utcOffset = this.utcOffset,
            startLatlng = this.startLatlng,
            endLatlng = this.endLatlng,
            locationCity = this.locationCity,
            locationState = this.locationState,
            locationCountry = this.locationCountry,
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
            visibility = this.visibility,
            flagged = this.flagged,
            gearId = this.gearId,
            fromAcceptedTag = this.fromAcceptedTag,
            uploadIdStr = this.uploadIdStr,
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

    data class ActivityAthlete(
        val id: Int = 0,
        val resourceState: Int = 0
    ) {
        fun toDomain(): StravaActivityDomain.ActivityAthleteDomain =
            StravaActivityDomain.ActivityAthleteDomain(id = this.id, resourceState = this.resourceState)
    }

    data class ActivityMap(
        val id: String = "",
        val summaryPolyline: String = "",
        val resourceState: Int = 0
    ) {
        fun toDomain(): StravaActivityDomain.ActivityMapDomain =
            StravaActivityDomain.ActivityMapDomain(
                id = this.id,
                summaryPolyline = this.summaryPolyline,
                resourceState = this.resourceState
            )
    }
}
