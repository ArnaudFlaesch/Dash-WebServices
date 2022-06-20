package com.dash.model

import com.fasterxml.jackson.annotation.JsonProperty

data class GameInfoResponse(
    val response: Games = Games()
)

data class Games(
    @JsonProperty("game_count")
    val gameCount: Int = 0,
    val games: List<GameInfo> = emptyList()
)
