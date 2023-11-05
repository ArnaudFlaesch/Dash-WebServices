package com.dash.app.controller.webservices

import com.dash.app.controller.response.Page
import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameInfoDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.domain.service.SteamWidgetService
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/steamWidget")
class SteamWidgetController(
    private val steamWidgetService: SteamWidgetService
) {
    @GetMapping("/playerData")
    fun getPlayerData(
        @RequestParam(value = "steamUserId") steamUserId: String
    ): List<PlayerDataDomain> = steamWidgetService.getPlayerData(steamUserId)

    @GetMapping("/ownedGames")
    fun getOwnedGames(
        @RequestParam(value = "steamUserId") steamUserId: String,
        @RequestParam(value = "search", defaultValue = "") search: String,
        @RequestParam(value = "pageNumber", defaultValue = "0") pageNumber: Int
    ): Page<GameInfoDomain> = steamWidgetService.getOwnedGames(steamUserId, search, pageNumber)

    @GetMapping("/achievementList")
    fun getAchievementList(
        @RequestParam(value = "appId") appId: String,
        @RequestParam(value = "steamUserId") steamUserId: String
    ): AchievementDataDomain = steamWidgetService.getAchievementList(appId, steamUserId)
}
