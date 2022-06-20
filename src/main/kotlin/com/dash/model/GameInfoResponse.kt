package com.dash.model

data class GameInfoResponse (
    val response: Games = Games()
)

data class Games(
    val game_count: Int = 0,
    val games : List<GameInfo> = emptyList()
)