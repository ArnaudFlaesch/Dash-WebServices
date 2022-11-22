package com.dash.infra.apimodel.steam

import com.dash.domain.model.steamwidget.PlayerDataDomain
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class PlayersDataApiResponse(
    val response: PlayersDataListResponse = PlayersDataListResponse()
) {
    fun toDomain(): List<PlayerDataDomain> = this.response.players.map(PlayerDataApi::toDomain)
}

data class PlayersDataListResponse(
    val players: List<PlayerDataApi> = listOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerDataApi(
    val personaname: String = "",
    val profileurl: String = "",
    val avatar: String = ""
) {
    fun toDomain(): PlayerDataDomain =
        PlayerDataDomain(personaname = this.personaname, avatar = this.avatar, profileurl = this.profileurl)
}
