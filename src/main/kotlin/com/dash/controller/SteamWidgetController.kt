package com.dash.controller

import com.dash.service.ProxyService
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
    private lateinit var steamApiKey: String

    @Value("\${dash.app.STEAM_USER_ID}")
    private lateinit var steamUserId: String

    val steamApiUrl = "https://api.steampowered.com"
    val getPlayerSummariesUrl = "/ISteamUser/GetPlayerSummaries/v0002/"
    val getOwnedGamesUrl = "/IPlayerService/GetOwnedGames/v0001/"
    val getAchievementsUrl = "/ISteamUserStats/GetPlayerAchievements/v0001"

    @GetMapping("/playerData")
    fun getPlayerData(): Any? {
        val getPlayerDataUrl = "$steamApiUrl$getPlayerSummariesUrl?key=$steamApiKey&steamids=$steamUserId"
        return proxyService.getDataFromProxy(getPlayerDataUrl)
    }

    @GetMapping("/ownedGames")
    fun getOwnedGames(): Any? {
        val getOwnedGamesUrl = "$steamApiUrl$getOwnedGamesUrl?key=$steamApiKey&steamid=$steamUserId&format=json&include_appinfo=true"
        return proxyService.getDataFromProxy(getOwnedGamesUrl)
    }

    @GetMapping("/achievementList")
    fun getAchievementList(@RequestParam(value = "appId") appId: String): Any? {
        val getAchievementsUrl = "$steamApiUrl$getAchievementsUrl/?appid=$appId&key=$steamApiKey&steamid=$steamUserId"
        return proxyService.getDataFromProxy(getAchievementsUrl)
    }
}
