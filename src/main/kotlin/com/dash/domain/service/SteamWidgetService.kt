package com.dash.domain.service

import com.dash.domain.model.steamwidget.*
import com.dash.infra.adapter.SteamWidgetAdapter
import org.springframework.stereotype.Service

@Service
class SteamWidgetService(private val steamWidgetAdapter: SteamWidgetAdapter) {

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> = steamWidgetAdapter.getPlayerData(steamUserId)

    fun getOwnedGames(steamUserId: String, search: String, pageNumber: Int): GameDataDomain =
        steamWidgetAdapter.getOwnedGames(steamUserId, search, pageNumber)

    fun getAchievementList(appId: String, steamUserId: String): AchievementDataDomain =
        steamWidgetAdapter.getAchievementList(appId, steamUserId)
}
