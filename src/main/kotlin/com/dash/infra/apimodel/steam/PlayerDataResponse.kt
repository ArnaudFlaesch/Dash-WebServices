package com.dash.infra.apimodel.steam

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class PlayersDataApiResponse(
    val response: PlayersDataListResponse = PlayersDataListResponse()
)

data class PlayersDataListResponse(
    val players: List<PlayerDataApi> = listOf()
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class PlayerDataApi(
    val personaname: String = "",
    val profileurl: String = "",
    val avatar: String = ""
)
