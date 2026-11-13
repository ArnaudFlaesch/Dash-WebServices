package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.SteamWidgetUseCase
import com.dash.dashboard.application.port.outbound.SteamPort
import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData
import com.dash.dashboard.domain.model.steam.searchByName
import com.dash.shared.domain.model.Page
import com.dash.shared.domain.model.toPage
import org.springframework.stereotype.Service

@Service
class SteamWidgetService(
    private val steamPort: SteamPort
) : SteamWidgetUseCase {
    companion object {
        private const val PAGE_SIZE = 25
    }

    override fun getPlayerData(steamUserId: String): List<PlayerData> = steamPort.getPlayerData(steamUserId)

    override fun getOwnedGames(
        steamUserId: String,
        search: String,
        pageNumber: Int
    ): Page<GameInfo> =
        steamPort
            .getOwnedGames(steamUserId)
            .searchByName(search)
            .toPage(pageNumber, PAGE_SIZE)

    override fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementData = steamPort.getAchievementList(appId, steamUserId)
}
