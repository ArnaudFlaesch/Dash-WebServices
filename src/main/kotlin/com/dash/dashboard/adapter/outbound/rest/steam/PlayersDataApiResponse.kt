package com.dash.dashboard.adapter.outbound.rest.steam

import com.dash.dashboard.domain.model.steam.PlayerData

data class PlayersDataApiResponse(
    val response: PlayersDataListResponse = PlayersDataListResponse()
)
