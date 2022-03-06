package com.dash.controller

import com.dash.controller.requests.GetStravaRefreshTokenPayload
import com.dash.controller.requests.GetStravaTokenPayload
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
    private val STRAVA_CLIENT_ID: String? = null

    @Value("\${dash.app.STRAVA_CLIENT_SECRET}")
    private val STRAVA_CLIENT_SECRET: String? = null

    private val STRAVA_TOKEN_URL = "https://www.strava.com/oauth/token"

    @PostMapping("/getToken")
    fun getToken(@RequestBody getStravaTokenPayload: GetStravaTokenPayload): String? {
        val url = "$STRAVA_TOKEN_URL?client_id=$STRAVA_CLIENT_ID&client_secret=$STRAVA_CLIENT_SECRET" +
            "&code=${getStravaTokenPayload.apiCode}&grant_type=authorization_code"
        return proxyService.postDataFromProxy(url, mapOf<String, Any>())
    }

    @PostMapping("/getRefreshToken")
    fun getRefreshToken(@RequestBody getStravaRefreshTokenPayload: GetStravaRefreshTokenPayload): String? {
        val url = "$STRAVA_TOKEN_URL?client_id=$STRAVA_CLIENT_ID&client_secret=$STRAVA_CLIENT_SECRET" +
            "&refresh_token=${getStravaRefreshTokenPayload.refreshToken}&grant_type=refresh_token"
        return proxyService.postDataFromProxy(url, mapOf<String, Any>())
    }

}
