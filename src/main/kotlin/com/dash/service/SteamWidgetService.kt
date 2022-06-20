package com.dash.service

import com.dash.model.GameInfo
import com.dash.model.GameInfoResponse
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

    val steamApiUrl = "https://api.steampowered.com"
    val getPlayerSummariesUrl = "/ISteamUser/GetPlayerSummaries/v0002/"
    val getOwnedGamesUrl = "/IPlayerService/GetOwnedGames/v0001/"
    val getAchievementsUrl = "/ISteamUserStats/GetPlayerAchievements/v0001"

    fun getPlayerData(): String? {
        val getPlayerDataUrl = "$steamApiUrl$getPlayerSummariesUrl?key=$steamApiKey&steamids=$steamUserId"
        return proxyService.getDataFromProxy(getPlayerDataUrl)
    }

    fun getOwnedGames(search: String, pageNumber: Int): GameInfoResponse {
        val PAGE_SIZE = 25
        val getOwnedGamesUrl = "$steamApiUrl$getOwnedGamesUrl?key=$steamApiKey&steamid=$steamUserId&format=json&include_appinfo=true"
        proxyService.getDataFromProxy(getOwnedGamesUrl).let { jsonGameData ->
            val gameData = ObjectMapper().readValue(jsonGameData, GameInfoResponse::class.java)
            val gamesList = gameData.response.games
                .sortedBy(GameInfo::name)
                .filter { gameInfo -> gameInfo.name.lowercase().contains(search.lowercase()) }
            val paginatedGames = if (gamesList.isEmpty() || gamesList.size <= PAGE_SIZE) {
                gamesList
            } else {
                var limit = if ((pageNumber + 1) * PAGE_SIZE < gameData.response.game_count) (pageNumber + 1) * PAGE_SIZE else gameData.response.game_count - 1
                gamesList.slice(pageNumber * PAGE_SIZE..limit)
            }
            return gameData.copy(response = gameData.response.copy(game_count = if(search.isBlank()) gameData.response.game_count else gamesList.size, games = paginatedGames))
        }
    }

    fun getAchievementList(appId: String): String? {
        val getAchievementsUrl = "$steamApiUrl$getAchievementsUrl/?appid=$appId&key=$steamApiKey&steamid=$steamUserId"
        return proxyService.getDataFromProxy(getAchievementsUrl)
    }
}