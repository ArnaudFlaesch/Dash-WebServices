package com.dash.domain.service

import com.dash.domain.model.steamwidget.GameInfo
import com.dash.domain.model.steamwidget.GameInfoResponse
import com.dash.domain.model.steamwidget.PlayerDataResponse
import com.dash.infra.rest.RestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SteamWidgetService {

    @Autowired
    private lateinit var proxyService: RestClient

    @Value("\${dash.app.STEAM_API_KEY}")
    private lateinit var steamApiKey: String

    companion object {
        private const val PAGE_SIZE = 25
        private const val steamApiUrl = "https://api.steampowered.com"
        private const val getPlayerSummariesUrl = "/ISteamUser/GetPlayerSummaries/v0002/"
        private const val getOwnedGamesUrl = "/IPlayerService/GetOwnedGames/v0001/"
        private const val getAchievementsUrl = "/ISteamUserStats/GetPlayerAchievements/v0001"
    }

    fun getPlayerData(steamUserId: String): PlayerDataResponse? {
        val getPlayerDataUrl = "$steamApiUrl$getPlayerSummariesUrl?key=$steamApiKey&steamids=$steamUserId"
        return proxyService.getDataFromProxy(getPlayerDataUrl, PlayerDataResponse::class)
    }

    fun getOwnedGames(steamUserId: String, search: String, pageNumber: Int): GameInfoResponse {
        val getOwnedGamesUrl = "$steamApiUrl$getOwnedGamesUrl?key=$steamApiKey&steamid=$steamUserId&format=json&include_appinfo=true"
        proxyService.getDataFromProxy(getOwnedGamesUrl, GameInfoResponse::class)?.let { gameData ->
            val gamesList = gameData.response.games
                .sortedBy(GameInfo::name)
                .filter { gameInfo -> gameInfo.name.lowercase().contains(search.lowercase()) }
            val paginatedGames = if (gamesList.isEmpty() || gamesList.size <= PAGE_SIZE) {
                gamesList
            } else {
                var limit = if ((pageNumber + 1) * PAGE_SIZE < gameData.response.gameCount) (pageNumber + 1) * PAGE_SIZE else gameData.response.gameCount - 1
                gamesList.slice(pageNumber * PAGE_SIZE..limit)
            }
            return gameData.copy(
                response =
                gameData.response.copy(
                    gameCount =
                    if (search.isBlank()) gameData.response.gameCount else gamesList.size,
                    games = paginatedGames
                )
            )
        }
        return GameInfoResponse()
    }

    fun getAchievementList(appId: String, steamUserId: String): String? {
        val getAchievementsUrl = "$steamApiUrl$getAchievementsUrl/?appid=$appId&key=$steamApiKey&steamid=$steamUserId"
        return proxyService.getDataFromProxy(getAchievementsUrl, String::class)
    }
}
