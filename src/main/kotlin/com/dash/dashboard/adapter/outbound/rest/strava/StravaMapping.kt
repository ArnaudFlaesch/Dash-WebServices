package com.dash.dashboard.adapter.outbound.rest.strava

import com.dash.dashboard.domain.model.strava.StravaActivity
import com.dash.dashboard.domain.model.strava.StravaAthlete
import com.dash.dashboard.domain.model.strava.StravaTokenData

fun StravaTokenDataResponse.toDomain(): StravaTokenData =
    StravaTokenData(
        accessToken = this.accessToken.orEmpty(),
        refreshToken = this.refreshToken.orEmpty(),
        expiresAt = this.expiresAt.orEmpty(),
        athlete = (this.athlete ?: StravaAthleteResponse()).toDomain()
    )

fun StravaAthleteResponse.toDomain(): StravaAthlete =
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

fun StravaActivityResponse.toDomain(): StravaActivity =
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

fun StravaActivityResponse.ActivityAthlete.toDomain(): StravaActivity.ActivityAthlete =
    StravaActivity.ActivityAthlete(
        id = this.id,
        resourceState = this.resourceState
    )

fun StravaActivityResponse.ActivityMap.toDomain(): StravaActivity.ActivityMap =
    StravaActivity.ActivityMap(
        id = this.id.orEmpty(),
        summaryPolyline = this.summaryPolyline.orEmpty(),
        resourceState = this.resourceState
    )
