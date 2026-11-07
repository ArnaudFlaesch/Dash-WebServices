package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.SteamWidgetUseCase
import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData
import com.dash.shared.domain.model.Page
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/steamWidget")
class SteamWidgetController(
    private val steamWidgetUseCase: SteamWidgetUseCase
) {
    @GetMapping("/playerData")
    fun getPlayerData(
        @RequestParam steamUserId: String
    ): List<PlayerData> = steamWidgetUseCase.getPlayerData(steamUserId)

    @GetMapping("/ownedGames")
    fun getOwnedGames(
        @RequestParam steamUserId: String,
        @RequestParam(defaultValue = "") search: String,
        @RequestParam(defaultValue = "0") pageNumber: Int
    ): Page<GameInfo> = steamWidgetUseCase.getOwnedGames(steamUserId, search, pageNumber)

    @GetMapping("/achievementList")
    fun getAchievementList(
        @RequestParam appId: String,
        @RequestParam steamUserId: String
    ): AchievementData = steamWidgetUseCase.getAchievementList(appId, steamUserId)
}
