package com.dash.service

import com.dash.model.steamwidget.GameInfo
import com.dash.model.steamwidget.GameInfoResponse
import com.dash.model.steamwidget.PlayerDataResponse
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class SteamWidgetService {

    @Autowired
    private lateinit var proxyService: ProxyService

    @Value("\${dash.app.STEAM_API_KEY}")
    private lateinit var steamApiKey: String

    @Value("\${dash.app.STEAM_USER_ID}")
    private lateinit var steamUserId: String

    companion object {
        const val PAGE_SIZE = 25
    }

    val steamApiUrl = "https://api.steampowered.com"
    val getPlayerSummariesUrl = "/ISteamUser/GetPlayerSummaries/v0002/"
    val getOwnedGamesUrl = "/IPlayerService/GetOwnedGames/v0001/"
    val getAchievementsUrl = "/ISteamUserStats/GetPlayerAchievements/v0001"

    fun getPlayerData(): PlayerDataResponse? {
        val getPlayerDataUrl = "$steamApiUrl$getPlayerSummariesUrl?key=$steamApiKey&steamids=$steamUserId"
        val test = proxyService.getDataFromProxy(getPlayerDataUrl)
        return ObjectMapper().readValue(test, PlayerDataResponse::class.java)
    }

    fun getOwnedGames(search: String, pageNumber: Int): GameInfoResponse {
        val getOwnedGamesUrl = "$steamApiUrl$getOwnedGamesUrl?key=$steamApiKey&steamid=$steamUserId&format=json&include_appinfo=true"
        proxyService.getDataFromProxy(getOwnedGamesUrl).let { jsonGameData ->
            val gameData = ObjectMapper().readValue(jsonGameData, GameInfoResponse::class.java)
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
                    if (search.isBlank()) gameData.response.gameCount
                    else gamesList.size,
                    games = paginatedGames
                )
            )
        }
    }

    fun getAchievementList(appId: String): String? {
        val getAchievementsUrl = "$steamApiUrl$getAchievementsUrl/?appid=$appId&key=$steamApiKey&steamid=$steamUserId"
        return proxyService.getDataFromProxy(getAchievementsUrl)
    }
}
