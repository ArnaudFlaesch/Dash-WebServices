package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.adapter.outbound.rest.ApiProperties
import com.dash.dashboard.adapter.outbound.rest.orNotFound
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
