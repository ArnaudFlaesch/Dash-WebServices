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
                .sortedBy(GameInfoApi::name)
                .filter { gameInfo -> gameInfo.name.contains(search, ignoreCase = true) }

        val paginatedGames =
            if (gamesList.size <= PAGE_SIZE) {
                gamesList
            } else {
                gamesList.drop(pageNumber * PAGE_SIZE).take(PAGE_SIZE)
            }

        val totalPages = gamesList.size / PAGE_SIZE
        return Page(
            content = paginatedGames.map(GameInfoApi::toDomain),
            totalPages = totalPages,
            totalElements = (if (search.isBlank()) ownedGamesResponse.gameCount else gamesList.size).toLong(),
            size = paginatedGames.size,
            last = (pageNumber + 1) == totalPages,
            number = pageNumber
        )
    }

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataDomain = steamApiClient.getAchievementList(appId, steamUserId).toDomain()
}
