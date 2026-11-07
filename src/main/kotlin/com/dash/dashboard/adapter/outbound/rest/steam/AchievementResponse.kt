package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.domain.model.steam.Achievement

data class AchievementResponse(
    val apiname: String = "",
    val achieved: Int = 0,
    val unlocktime: Int = 0
) {
    fun toDomain(): Achievement =
        Achievement(
            apiname = this.apiname,
            achieved = this.achieved,
            unlocktime = this.unlocktime
        )
}
