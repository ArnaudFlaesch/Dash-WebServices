package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.domain.model.steam.PlayerData

data class PlayerDataApi(
    val personaname: String = "",
    val profileurl: String = "",
    val avatar: String = ""
) {
    fun toDomain(): PlayerData =
        PlayerData(
            personaname = this.personaname,
            avatar = this.avatar,
            profileurl = this.profileurl
        )
}
