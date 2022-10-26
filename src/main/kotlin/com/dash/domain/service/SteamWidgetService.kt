package com.dash.domain.service

import com.dash.domain.model.steamWidget.*
import com.dash.infra.adapter.SteamWidgetAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SteamWidgetService {

    @Autowired
    private lateinit var steamWidgetAdapter: SteamWidgetAdapter

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> = steamWidgetAdapter.getPlayerData(steamUserId)

    fun getOwnedGames(steamUserId: String, search: String, pageNumber: Int): GameDataDomain =
        steamWidgetAdapter.getOwnedGames(steamUserId, search, pageNumber)

    fun getAchievementList(appId: String, steamUserId: String): AchievementDataDomain =
        steamWidgetAdapter.getAchievementList(appId, steamUserId)
}
