package com.dash.infra.adapter

import com.dash.domain.model.Page
import com.dash.domain.model.map
import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameInfoDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.domain.model.toPage
import com.dash.infra.apimodel.steam.GameInfoApi
import com.dash.infra.rest.SteamApiClient
import org.springframework.stereotype.Component

@Component
class SteamWidgetAdapter(
    private val steamApiClient: SteamApiClient
) {
    companion object {
        private const val PAGE_SIZE = 25
    }

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> =
        steamApiClient.getPlayerData(steamUserId).toDomain()

    fun getOwnedGames(
        steamUserId: String,
        search: String,
        pageNumber: Int
    ): Page<GameInfoDomain> =
        steamApiClient
            .getOwnedGames(steamUserId)
            .response.games
            .filter { gameInfo -> gameInfo.name.contains(search, ignoreCase = true) }
            .sortedBy(GameInfoApi::name)
            .toPage(pageNumber, PAGE_SIZE)
            .map(GameInfoApi::toDomain)

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataDomain = steamApiClient.getAchievementList(appId, steamUserId).toDomain()
}
