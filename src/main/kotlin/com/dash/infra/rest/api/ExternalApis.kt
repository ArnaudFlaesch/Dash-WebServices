package com.dash.infra.rest.api

import com.dash.infra.apimodel.openweather.OpenWeatherForecastResponse
import com.dash.infra.apimodel.openweather.OpenWeatherWeatherResponse
import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import com.dash.infra.apimodel.strava.StravaActivityResponse
import com.dash.infra.apimodel.strava.StravaAthleteResponse
import com.dash.infra.apimodel.strava.StravaTokenDataResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange
import org.springframework.web.service.annotation.PostExchange

// Declarative HTTP clients, registered in HttpServicesConfig. Base URLs and default headers come from
// spring.http.serviceclient.<group> in application.yml. A null return value means an empty response body.

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface AirParifApi {
    @GetExchange("/commune")
    fun getPrevisionCommune(
        @RequestParam("insee") communeInseeCode: String
    ): Map<String, List<Map<String, String>>>?

    @GetExchange("/couleurs")
    fun getColors(): Map<String, String>?
}

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface OpenWeatherApi {
    @GetExchange("/weather")
    fun getWeather(
        @RequestParam("units") units: String,
        @RequestParam("lang") lang: String,
        @RequestParam("appid") apiKey: String,
        @RequestParam("q") city: String
    ): OpenWeatherWeatherResponse?

    @GetExchange("/forecast")
    fun getForecast(
        @RequestParam("units") units: String,
        @RequestParam("lang") lang: String,
        @RequestParam("appid") apiKey: String,
        @RequestParam("q") city: String
    ): OpenWeatherForecastResponse?
}

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface SteamApi {
    @GetExchange("/ISteamUser/GetPlayerSummaries/v0002/")
    fun getPlayerSummaries(
        @RequestParam("key") apiKey: String,
        @RequestParam("steamids") steamUserId: String
    ): PlayersDataApiResponse?

    @GetExchange("/IPlayerService/GetOwnedGames/v0001/")
    fun getOwnedGames(
        @RequestParam("key") apiKey: String,
        @RequestParam("steamid") steamUserId: String,
        @RequestParam("format") format: String,
        @RequestParam("include_appinfo") includeAppInfo: Boolean
    ): GameInfoResponse?

    @GetExchange("/ISteamUserStats/GetPlayerAchievements/v0001/")
    fun getPlayerAchievements(
        @RequestParam("appid") appId: String,
        @RequestParam("key") apiKey: String,
        @RequestParam("steamid") steamUserId: String
    ): AchievementDataResponse?
}

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface StravaApi {
    @PostExchange("/oauth/token")
    fun getToken(
        @RequestParam params: Map<String, String>,
        @RequestBody body: Map<String, Any>
    ): StravaTokenDataResponse?

    @GetExchange("/api/v3/athlete")
    fun getAthlete(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String
    ): StravaAthleteResponse?

    @GetExchange("/api/v3/athlete/activities")
    fun getAthleteActivities(
        @RequestHeader(HttpHeaders.AUTHORIZATION) authorization: String,
        @RequestParam("page") pageNumber: Int,
        @RequestParam("per_page") numberOfActivities: Int
    ): List<StravaActivityResponse>?
}
