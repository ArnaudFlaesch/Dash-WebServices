package com.dash.infra.apimodel.steam

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class AchievementDataResponse(
    val playerstats: PlayerStatsResponse = PlayerStatsResponse()
)

data class PlayerStatsResponse(
    val achievements: List<AchievementResponse> = listOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AchievementResponse(
    val apiname: String = "",
    val achieved: Int = 0,
    val unlocktime: Int = 0
)
