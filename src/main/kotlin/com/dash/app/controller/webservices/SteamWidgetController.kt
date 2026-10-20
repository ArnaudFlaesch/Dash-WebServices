package com.dash.app.controller.webservices

import com.dash.app.controller.response.Page
import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameInfoDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.domain.service.SteamWidgetService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/steamWidget")
class SteamWidgetController(
    private val steamWidgetService: SteamWidgetService
) {
    @GetMapping("/playerData")
    fun getPlayerData(
        @RequestParam steamUserId: String
    ): List<PlayerDataDomain> = steamWidgetService.getPlayerData(steamUserId)

    @GetMapping("/ownedGames")
    fun getOwnedGames(
        @RequestParam steamUserId: String,
        @RequestParam(defaultValue = "") search: String,
        @RequestParam(defaultValue = "0") pageNumber: Int
    ): Page<GameInfoDomain> = steamWidgetService.getOwnedGames(steamUserId, search, pageNumber)

    @GetMapping("/achievementList")
    fun getAchievementList(
        @RequestParam appId: String,
        @RequestParam steamUserId: String
    ): AchievementDataDomain = steamWidgetService.getAchievementList(appId, steamUserId)
}
