package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.domain.model.weather.*

data class WeatherConditionResponse(
    val id: Int = 0,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
) {
    fun toDomain(): WeatherCondition =
        WeatherCondition(
            id = this.id,
            main = this.main,
            description = this.description,
            icon = this.icon
        )
}
