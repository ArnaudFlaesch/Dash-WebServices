package com.dash.infra.adapter

import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameDataDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoApi
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import com.dash.infra.rest.SteamApiClient
import org.springframework.stereotype.Service

@Service
class SteamWidgetAdapter(
    private val steamApiClient: SteamApiClient
) {

    companion object {
        private const val PAGE_SIZE = 25
    }

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> {
        val playerDataResponse = steamApiClient.getPlayerData(steamUserId) ?: PlayersDataApiResponse()
        return playerDataResponse.toDomain()
    }

    fun getOwnedGames(steamUserId: String, search: String, pageNumber: Int): GameDataDomain {
        val ownedGamesResponse = steamApiClient.getOwnedGames(steamUserId) ?: GameInfoResponse()

        val gamesList = ownedGamesResponse.response.games
            .sortedBy(GameInfoApi::name)
            .filter { gameInfo -> gameInfo.name.lowercase().contains(search.lowercase()) }
        val paginatedGames = if (gamesList.isEmpty() || gamesList.size <= PAGE_SIZE) {
            gamesList
        } else {
            val limit =
                if ((pageNumber + 1) * PAGE_SIZE < ownedGamesResponse.response.gameCount) {
                    (pageNumber + 1) * PAGE_SIZE - 1
                } else {
                    ownedGamesResponse.response.gameCount - 1
                }
            gamesList.slice(pageNumber * PAGE_SIZE..limit)
        }
        val filteredResponse = ownedGamesResponse.copy(
            response =
            ownedGamesResponse.response.copy(
                gameCount =
                if (search.isBlank()) ownedGamesResponse.response.gameCount else gamesList.size,
                games = paginatedGames
            )
        )
        return filteredResponse.toDomain()
    }

    fun getAchievementList(appId: String, steamUserId: String): AchievementDataDomain {
        val achievementsDataResponse = steamApiClient.getAchievementList(appId, steamUserId) ?: AchievementDataResponse()
        return achievementsDataResponse.toDomain()
    }
}
