package com.dash.infra.adapter

import com.dash.domain.mapping.SteamWidgetMapper
import com.dash.domain.model.steamWidget.AchievementDataDomain
import com.dash.domain.model.steamWidget.GameDataDomain
import com.dash.domain.model.steamWidget.PlayerDataDomain
import com.dash.infra.apimodel.steam.AchievementDataResponse
import com.dash.infra.apimodel.steam.GameInfoApi
import com.dash.infra.apimodel.steam.GameInfoResponse
import com.dash.infra.apimodel.steam.PlayersDataApiResponse
import com.dash.infra.rest.SteamApiClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SteamWidgetAdapter {

    @Autowired
    private lateinit var steamApiClient: SteamApiClient

    @Autowired
    private lateinit var steamWidgetMapper: SteamWidgetMapper

    companion object {
        private const val PAGE_SIZE = 25
    }

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> {
        val playerDataResponse = steamApiClient.getPlayerData(steamUserId) ?: PlayersDataApiResponse()
        return steamWidgetMapper.playersDataResponseToDomain(playerDataResponse)
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
                    (pageNumber + 1) * PAGE_SIZE
                } else { ownedGamesResponse.response.gameCount - 1 }
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
        return steamWidgetMapper.gameDataResponseToDomain(filteredResponse)
    }

    fun getAchievementList(appId: String, steamUserId: String): AchievementDataDomain {
        val achievementsDataResponse = steamApiClient.getAchievementList(appId, steamUserId) ?: AchievementDataResponse()
        return steamWidgetMapper.achievementsApiResponseToDomain(achievementsDataResponse)
    }
}
