package com.dash.controller

import com.dash.controller.requests.stravaWidget.GetStravaRefreshTokenPayload
import com.dash.controller.requests.stravaWidget.GetStravaTokenPayload
import com.dash.service.ProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/stravaWidget")
class StravaWidgetController {

    @Autowired
    private lateinit var proxyService: ProxyService

    @Value("\${dash.app.STRAVA_CLIENT_ID}")
    private lateinit var stravaClientId: String

    @Value("\${dash.app.STRAVA_CLIENT_SECRET}")
    private lateinit var stravaClientSecret: String

    private val stravaTokenUrl = "https://www.strava.com/oauth/token"

    @PostMapping("/getToken")
    fun getToken(@RequestBody getStravaTokenPayload: GetStravaTokenPayload): String? {
        val url = "$stravaTokenUrl?client_id=$stravaClientId&client_secret=$stravaClientSecret" +
            "&code=${getStravaTokenPayload.apiCode}&grant_type=authorization_code"
        return proxyService.postDataFromProxy(url, mapOf<String, Any>())
    }

    @PostMapping("/getRefreshToken")
    fun getRefreshToken(@RequestBody getStravaRefreshTokenPayload: GetStravaRefreshTokenPayload): String? {
        val url = "$stravaTokenUrl?client_id=$stravaClientId&client_secret=$stravaClientSecret" +
            "&refresh_token=${getStravaRefreshTokenPayload.refreshToken}&grant_type=refresh_token"
        return proxyService.postDataFromProxy(url, mapOf<String, Any>())
    }
}
