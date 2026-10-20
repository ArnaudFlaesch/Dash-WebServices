package com.dash.infra.rest

import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import com.dash.infra.rest.api.SteamApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SteamApiClient(
    private val steamApi: SteamApi,
    @Value("\${dash.app.STEAM_API_KEY}")
    private val steamApiKey: String
) {
    fun getPlayerData(steamUserId: String): PlayersDataApiResponse =
        steamApi.getPlayerSummaries(steamApiKey, steamUserId).orNotFound()

    fun getOwnedGames(steamUserId: String): GameInfoResponse =
        steamApi.getOwnedGames(steamApiKey, steamUserId, format = "json", includeAppInfo = true).orNotFound()

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataResponse = steamApi.getPlayerAchievements(appId, steamApiKey, steamUserId).orNotFound()
}
