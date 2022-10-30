package com.dash.domain.model.steamWidget

data class AchievementDataDomain(
    val playerstats: PlayerStatsDomain
)

data class PlayerStatsDomain(
    val achievements: List<AchievementDomain>
)

data class AchievementDomain(
    val apiname: String,
    val achieved: Int,
    val unlocktime: Int
)
