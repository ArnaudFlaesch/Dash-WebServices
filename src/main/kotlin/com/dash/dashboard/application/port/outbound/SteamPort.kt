package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData

interface SteamPort {
    fun getPlayerData(steamUserId: String): List<PlayerData>

    fun getOwnedGames(steamUserId: String): List<GameInfo>

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementData
}
