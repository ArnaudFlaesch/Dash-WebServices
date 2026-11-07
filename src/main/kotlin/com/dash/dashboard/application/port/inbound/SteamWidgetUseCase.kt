package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData
import com.dash.shared.domain.model.Page

interface SteamWidgetUseCase {
    fun getPlayerData(steamUserId: String): List<PlayerData>

    /** Returns a page of the user's games whose name contains [search], sorted by name. */
    fun getOwnedGames(
        steamUserId: String,
        search: String,
        pageNumber: Int
    ): Page<GameInfo>

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementData
}
