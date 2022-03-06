package com.dash.controller

import com.dash.service.ProxyService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*


@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/steamWidget")
class SteamWidgetController {

    @Autowired
    private lateinit var proxyService: ProxyService

    @Value("\${dash.app.STEAM_API_KEY}")
    private val STEAM_API_KEY: String? = null

    @Value("\${dash.app.STEAM_USER_ID}")
    private val STEAM_USER_ID: String? = null

    val STEAM_API_URL = "https://api.steampowered.com"
    val GET_PLAYER_SUMMARIES_URL = "/ISteamUser/GetPlayerSummaries/v0002/"
    val GET_OWNED_GAMES_URL = "/IPlayerService/GetOwnedGames/v0001/"
    val GET_ACHIEVEMENTS_URL = "/ISteamUserStats/GetPlayerAchievements/v0001"

    @GetMapping("/playerData")
    fun getPlayerData(): Any? {
        val getPlayerDataUrl = "$STEAM_API_URL$GET_PLAYER_SUMMARIES_URL?key=$STEAM_API_KEY&steamids=$STEAM_USER_ID"
        return proxyService.getDataFromProxy(getPlayerDataUrl)
    }

    @GetMapping("/ownedGames")
    fun getOwnedGames(): Any? {
        val getOwnedGamesUrl = "$STEAM_API_URL$GET_OWNED_GAMES_URL?key=$STEAM_API_KEY&steamid=$STEAM_USER_ID&format=json&include_appinfo=true"
        return proxyService.getDataFromProxy(getOwnedGamesUrl)
    }

    @GetMapping("/achievementList")
    fun getAchievementList(@RequestParam(value = "appId") appId: String): Any? {
        val getAchievementsUrl = "$STEAM_API_URL$GET_ACHIEVEMENTS_URL/?appid=${appId}&key=${STEAM_API_KEY}&steamid=${STEAM_USER_ID}"
        return proxyService.getDataFromProxy(getAchievementsUrl)
    }

}
