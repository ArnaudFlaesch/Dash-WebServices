package com.dash.infra.adapter

import com.dash.app.controller.response.Page
import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameInfoDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
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
    ): Page<GameInfoDomain> {
        val ownedGamesResponse = steamApiClient.getOwnedGames(steamUserId).response

        val gamesList =
            ownedGamesResponse.games
                .filter { gameInfo -> gameInfo.name.contains(search, ignoreCase = true) }
                .sortedBy(GameInfoApi::name)

        val paginatedGames = gamesList.drop(pageNumber.coerceAtLeast(0) * PAGE_SIZE).take(PAGE_SIZE)

        // Rounded up, so that a partially filled last page is counted
        val totalPages = (gamesList.size + PAGE_SIZE - 1) / PAGE_SIZE
        return Page(
            content = paginatedGames.map(GameInfoApi::toDomain),
            totalPages = totalPages,
            totalElements = gamesList.size.toLong(),
            size = paginatedGames.size,
            last = pageNumber >= totalPages - 1,
            number = pageNumber
        )
    }

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataDomain = steamApiClient.getAchievementList(appId, steamUserId).toDomain()
}
