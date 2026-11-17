package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.SteamWidgetUseCase
import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData
import com.dash.shared.domain.model.Page
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/steam/players/{steamUserId}")
class SteamWidgetController(
    private val steamWidgetUseCase: SteamWidgetUseCase
) {
    @GetMapping
    fun getPlayerData(
        @PathVariable steamUserId: String
    ): List<PlayerData> = steamWidgetUseCase.getPlayerData(steamUserId)

    @GetMapping("/games")
    fun getOwnedGames(
        @PathVariable steamUserId: String,
        @RequestParam(defaultValue = "") search: String,
        @RequestParam(defaultValue = "0") pageNumber: Int
    ): Page<GameInfo> = steamWidgetUseCase.getOwnedGames(steamUserId, search, pageNumber)

    @GetMapping("/games/{appId}/achievements")
    fun getAchievementList(
        @PathVariable steamUserId: String,
        @PathVariable appId: String
    ): AchievementData = steamWidgetUseCase.getAchievementList(appId, steamUserId)
}
