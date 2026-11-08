package com.dash.dashboard.adapter.outbound.rest.steam

import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class GameDataApi(
    val gameCount: Int = 0,
    val games: List<GameInfoApi> = emptyList()
)
