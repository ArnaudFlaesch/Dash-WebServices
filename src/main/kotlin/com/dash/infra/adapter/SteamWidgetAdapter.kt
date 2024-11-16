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

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> {
        val playerDataResponse = steamApiClient.getPlayerData(steamUserId)
        return playerDataResponse.toDomain()
    }

    fun getOwnedGames(
        steamUserId: String,
        search: String,
        pageNumber: Int
    ): Page<GameInfoDomain> {
        val ownedGamesResponse = steamApiClient.getOwnedGames(steamUserId)

        val gamesList =
            ownedGamesResponse.response.games
                .sortedBy(GameInfoApi::name)
                .filter { gameInfo -> gameInfo.name.lowercase().contains(search.lowercase()) }

        val paginatedGames =
            if (gamesList.isEmpty() || gamesList.size <= PAGE_SIZE) {
                gamesList
            } else {
                val limit =
                    if ((pageNumber + 1) * PAGE_SIZE < gamesList.size) {
                        (pageNumber + 1) * PAGE_SIZE - 1
                    } else {
                        gamesList.size - 1
                    }
                gamesList.slice(pageNumber * PAGE_SIZE..limit)
            }

        val totalPages = gamesList.size / PAGE_SIZE
        return Page(
            content = paginatedGames.map(GameInfoApi::toDomain),
            totalPages = totalPages,
            totalElements =
                (if (search.isBlank()) ownedGamesResponse.response.gameCount else gamesList.size)
                    .toLong(),
            size = paginatedGames.size,
            last = (pageNumber + 1) == totalPages,
            number = pageNumber
        )
    }

    fun getAchievementList(
        appId: String,
        steamUserId: String
    ): AchievementDataDomain {
        val achievementsDataResponse = steamApiClient.getAchievementList(appId, steamUserId)
        return achievementsDataResponse.toDomain()
    }
}
