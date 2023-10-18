package com.dash.infra.rest

import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SteamApiClient(
    private val restClient: RestClient,
    @Value("\${dash.app.STEAM_API_URL}")
    private val steamApiUrl: String,
    @Value("\${dash.app.STEAM_API_KEY}")
    private val steamApiKey: String
) {
    companion object {
        private const val GET_PLAYER_SUMMARIES_URL = "/ISteamUser/GetPlayerSummaries/v0002/"
        private const val GET_OWNED_GAMES_URL = "/IPlayerService/GetOwnedGames/v0001/"
        private const val GET_ACHIEVEMENTS_URL = "/ISteamUserStats/GetPlayerAchievements/v0001"
    }

    fun getPlayerData(steamUserId: String): PlayersDataApiResponse {
        val getPlayerDataUrl = "${steamApiUrl}$GET_PLAYER_SUMMARIES_URL?key=$steamApiKey&steamids=$steamUserId"
        return restClient.getDataFromProxy(getPlayerDataUrl, PlayersDataApiResponse::class)
    }

    fun getOwnedGames(steamUserId: String): GameInfoResponse {
        val getOwnedGamesUrl = "${steamApiUrl}$GET_OWNED_GAMES_URL?key=$steamApiKey&steamid=$steamUserId&format=json&include_appinfo=true"
        return restClient.getDataFromProxy(getOwnedGamesUrl, GameInfoResponse::class)
    }

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataResponse {
        val getAchievementsUrl = "${steamApiUrl}$GET_ACHIEVEMENTS_URL/?appid=$appId&key=$steamApiKey&steamid=$steamUserId"
        return restClient.getDataFromProxy(getAchievementsUrl, AchievementDataResponse::class)
    }
}
