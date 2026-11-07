package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.application.port.outbound.SteamPort
import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.GameInfo
import com.dash.dashboard.domain.model.steam.PlayerData
import org.springframework.stereotype.Component

@Component
class SteamAdapter(
    private val steamApiClient: SteamApiClient
) : SteamPort {
    override fun getPlayerData(steamUserId: String): List<PlayerData> =
        steamApiClient.getPlayerData(steamUserId).toDomain()

    override fun getOwnedGames(steamUserId: String): List<GameInfo> =
        steamApiClient
            .getOwnedGames(steamUserId)
            .response.games
            .map(GameInfoApi::toDomain)

    override fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementData = steamApiClient.getAchievementList(appId, steamUserId).toDomain()
}
