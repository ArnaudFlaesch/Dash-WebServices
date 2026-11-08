package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.domain.model.weather.*

data class OpenWeatherForecastResponse(
    val cod: String = "",
    val message: Int = 0,
    val cnt: Int = 0,
    val list: List<ForecastResponse> = listOf(),
    val city: CityResponse = CityResponse()
)
