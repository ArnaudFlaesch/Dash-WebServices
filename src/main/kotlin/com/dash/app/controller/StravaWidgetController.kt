package com.dash.app.controller

import com.dash.app.controller.requests.stravaWidget.GetStravaRefreshTokenPayload
import com.dash.app.controller.requests.stravaWidget.GetStravaTokenPayload
import com.dash.domain.model.stravaWidget.StravaActivityDomain
import com.dash.domain.model.stravaWidget.StravaAthleteDomain
import com.dash.domain.model.stravaWidget.StravaTokenDataDomain
import com.dash.domain.service.StravaWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/stravaWidget")
class StravaWidgetController {

    @Autowired
    private lateinit var stravaWidgetService: StravaWidgetService

    @PostMapping("/getToken")
    fun getToken(@RequestBody getStravaTokenPayload: GetStravaTokenPayload): StravaTokenDataDomain =
        stravaWidgetService.getToken(getStravaTokenPayload.apiCode)

    @PostMapping("/getRefreshToken")
    fun getRefreshToken(@RequestBody getStravaRefreshTokenPayload: GetStravaRefreshTokenPayload): StravaTokenDataDomain =
        stravaWidgetService.getRefreshToken(getStravaRefreshTokenPayload.refreshToken)

    @GetMapping("/getAthleteData")
    fun getAthleteData(@RequestParam("token") token: String): StravaAthleteDomain =
        stravaWidgetService.getAthleteData(token)

    @GetMapping("/getAthleteActivities")
    fun getAthleteActivities(
        @RequestParam("token") token: String,
        @RequestParam("numberOfActivities") numberOfActivities: Int?
    ): List<StravaActivityDomain> =
        stravaWidgetService.getAthleteActivities(token, numberOfActivities)
}
