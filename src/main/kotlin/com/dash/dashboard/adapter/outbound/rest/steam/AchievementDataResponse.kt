package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.domain.model.steam.AchievementData
import com.dash.dashboard.domain.model.steam.PlayerStats

data class AchievementDataResponse(
    val playerstats: PlayerStatsResponse = PlayerStatsResponse()
)
