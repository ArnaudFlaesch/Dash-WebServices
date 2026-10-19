package com.dash.infra.rest

import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class SteamApiClient(
    private val proxyClient: ProxyClient,
    @Value("\${dash.app.STEAM_API_URL}")
    private val steamApiUrl: String,
    @Value("\${dash.app.STEAM_API_KEY}")
    private val steamApiKey: String
) {
    companion object {
        private const val GET_PLAYER_SUMMARIES_URL = "/ISteamUser/GetPlayerSummaries/v0002/"
        private const val GET_OWNED_GAMES_URL = "/IPlayerService/GetOwnedGames/v0001/"
        private const val GET_ACHIEVEMENTS_URL = "/ISteamUserStats/GetPlayerAchievements/v0001/"
    }

    fun getPlayerData(steamUserId: String): PlayersDataApiResponse =
        proxyClient.get(
            buildUri(steamApiUrl, GET_PLAYER_SUMMARIES_URL, "key" to steamApiKey, "steamids" to steamUserId),
            typeReference()
        )

    fun getOwnedGames(steamUserId: String): GameInfoResponse =
        proxyClient.get(
            buildUri(
                steamApiUrl,
                GET_OWNED_GAMES_URL,
                "key" to steamApiKey,
                "steamid" to steamUserId,
                "format" to "json",
                "include_appinfo" to true
            ),
            typeReference()
        )

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataResponse =
        proxyClient.get(
            buildUri(
                steamApiUrl,
                GET_ACHIEVEMENTS_URL,
                "appid" to appId,
                "key" to steamApiKey,
                "steamid" to steamUserId
            ),
            typeReference()
        )
}
