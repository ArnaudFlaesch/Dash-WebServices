package com.dash.app.controller

import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameDataDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.domain.service.SteamWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/steamWidget")
class SteamWidgetController {

    @Autowired
    private lateinit var steamWidgetService: SteamWidgetService

    @GetMapping("/playerData")
    fun getPlayerData(
        @RequestParam(value = "steamUserId") steamUserId: String
    ): List<PlayerDataDomain> = steamWidgetService.getPlayerData(steamUserId)

    @GetMapping("/ownedGames")
    fun getOwnedGames(
        @RequestParam(value = "steamUserId") steamUserId: String,
        @RequestParam(value = "search", defaultValue = "") search: String,
        @RequestParam(value = "pageNumber", defaultValue = "0") pageNumber: Int
    ): GameDataDomain = steamWidgetService.getOwnedGames(steamUserId, search, pageNumber)

    @GetMapping("/achievementList")
    fun getAchievementList(
        @RequestParam(value = "appId") appId: String,
        @RequestParam(value = "steamUserId") steamUserId: String
    ): AchievementDataDomain = steamWidgetService.getAchievementList(appId, steamUserId)

}
