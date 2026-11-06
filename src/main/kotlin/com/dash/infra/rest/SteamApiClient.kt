package com.dash.infra.rest

import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import com.dash.infra.rest.api.SteamApi
import org.springframework.stereotype.Component

@Component
class SteamApiClient(
    private val steamApi: SteamApi,
    apiProperties: ApiProperties
) {
    private val steamApiKey = apiProperties.steam.key

    fun getPlayerData(steamUserId: String): PlayersDataApiResponse =
        steamApi.getPlayerSummaries(steamApiKey, steamUserId).orNotFound()

    fun getOwnedGames(steamUserId: String): GameInfoResponse =
        steamApi.getOwnedGames(steamApiKey, steamUserId, format = "json", includeAppInfo = true).orNotFound()

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataResponse = steamApi.getPlayerAchievements(appId, steamApiKey, steamUserId).orNotFound()
}
