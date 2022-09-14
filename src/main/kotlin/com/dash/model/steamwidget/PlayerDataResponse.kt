package com.dash.model.steamwidget

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class PlayerDataResponse(
    val response: PlayersData = PlayersData()
)

data class PlayersData(
    val players: List<PlayerData> = listOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerData(
    val personaname: String = "",
    val profileurl: String = "",
    val avatar: String = ""
)
