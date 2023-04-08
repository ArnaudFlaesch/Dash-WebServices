package com.dash.domain.service

import com.dash.app.controller.response.Page
import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.GameInfoDomain
import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.dash.infra.adapter.SteamWidgetAdapter
import org.springframework.stereotype.Service

@Service
class SteamWidgetService(private val steamWidgetAdapter: SteamWidgetAdapter) {

    fun getPlayerData(steamUserId: String): List<PlayerDataDomain> = steamWidgetAdapter.getPlayerData(steamUserId)

    fun getOwnedGames(steamUserId: String, search: String, pageNumber: Int): Page<GameInfoDomain> =
        steamWidgetAdapter.getOwnedGames(steamUserId, search, pageNumber)

    fun getAchievementList(appId: String, steamUserId: String): AchievementDataDomain =
        steamWidgetAdapter.getAchievementList(appId, steamUserId)
}
