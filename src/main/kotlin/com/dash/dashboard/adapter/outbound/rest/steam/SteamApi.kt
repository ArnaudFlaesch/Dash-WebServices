package com.dash.dashboard.adapter.outbound.rest.steam

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

// Declarative HTTP client, registered in RestClientConfig. Its base URL and default headers come from
// spring.http.serviceclient.steam in application.yml. A null return value means an empty response body.

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
