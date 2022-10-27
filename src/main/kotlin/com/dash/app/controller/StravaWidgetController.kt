package com.dash.app.controller

import com.dash.app.controller.requests.stravaWidget.GetStravaRefreshTokenPayload
import com.dash.app.controller.requests.stravaWidget.GetStravaTokenPayload
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
    fun getToken(@RequestBody getStravaTokenPayload: GetStravaTokenPayload): StravaTokenDataDomain {
        return stravaWidgetService.getToken(getStravaTokenPayload.apiCode)
    }

    @PostMapping("/getRefreshToken")
    fun getRefreshToken(@RequestBody getStravaRefreshTokenPayload: GetStravaRefreshTokenPayload): StravaTokenDataDomain {
        return stravaWidgetService.getRefreshToken(getStravaRefreshTokenPayload.refreshToken)
    }
}
