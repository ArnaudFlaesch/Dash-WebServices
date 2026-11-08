package com.dash.dashboard.adapter.outbound.rest.steam

data class PlayerStatsResponse(
    val achievements: List<AchievementResponse> = listOf()
)
