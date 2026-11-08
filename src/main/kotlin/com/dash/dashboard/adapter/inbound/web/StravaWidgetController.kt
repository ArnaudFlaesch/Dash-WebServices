package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.adapter.inbound.web.request.strava.GetStravaRefreshTokenRequest
import com.dash.dashboard.adapter.inbound.web.request.strava.GetStravaTokenRequest
import com.dash.dashboard.application.port.inbound.StravaWidgetUseCase
import com.dash.dashboard.domain.model.strava.StravaActivity
import com.dash.dashboard.domain.model.strava.StravaAthlete
import com.dash.dashboard.domain.model.strava.StravaTokenData
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/stravaWidget")
class StravaWidgetController(
    private val stravaWidgetUseCase: StravaWidgetUseCase
) {
    companion object {
        private const val PAGE_NUMBER = 1
        private const val NUMBER_OF_ACTIVITIES_PER_PAGE = 25
    }

    @PostMapping("/getToken")
    fun getToken(
        @RequestBody getStravaTokenRequest: GetStravaTokenRequest
    ): StravaTokenData = stravaWidgetUseCase.getToken(getStravaTokenRequest.apiCode)

    @PostMapping("/getRefreshToken")
    fun getRefreshToken(
        @RequestBody getStravaRefreshTokenRequest: GetStravaRefreshTokenRequest
    ): StravaTokenData = stravaWidgetUseCase.getRefreshToken(getStravaRefreshTokenRequest.refreshToken)

    @GetMapping("/getAthleteData")
    fun getAthleteData(
        @RequestParam token: String
    ): StravaAthlete = stravaWidgetUseCase.getAthleteData(token)

    @GetMapping("/getAthleteActivities")
    fun getAthleteActivities(
        @RequestParam token: String,
        @RequestParam pageNumber: Int = PAGE_NUMBER,
        @RequestParam numberOfActivities: Int = NUMBER_OF_ACTIVITIES_PER_PAGE
    ): List<StravaActivity> = stravaWidgetUseCase.getAthleteActivities(token, pageNumber, numberOfActivities)
}
