package com.dash.model.steamwidget

import com.fasterxml.jackson.annotation.JsonProperty

data class GameInfoResponse(
    val response: GameData = GameData()
)

data class GameData(
    @JsonProperty("game_count")
    val gameCount: Int = 0,
    @JsonProperty("games")
    val games: List<GameInfo> = emptyList()
)
