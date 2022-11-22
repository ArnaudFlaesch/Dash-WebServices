package com.dash.infra.apimodel.steam

import com.dash.domain.model.steamwidget.AchievementDataDomain
import com.dash.domain.model.steamwidget.AchievementDomain
import com.dash.domain.model.steamwidget.PlayerStatsDomain
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class AchievementDataResponse(
    val playerstats: PlayerStatsResponse = PlayerStatsResponse()
) {
    fun toDomain(): AchievementDataDomain =
        AchievementDataDomain(
            playerstats = PlayerStatsDomain(
                achievements = this.playerstats.achievements.map(AchievementResponse::toDomain)
            )
        )
}

data class PlayerStatsResponse(
    val achievements: List<AchievementResponse> = listOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class AchievementResponse(
    val apiname: String = "",
    val achieved: Int = 0,
    val unlocktime: Int = 0
) {
    fun toDomain(): AchievementDomain =
        AchievementDomain(
            apiname = this.apiname,
            achieved = this.achieved,
            unlocktime = this.unlocktime
        )
}
