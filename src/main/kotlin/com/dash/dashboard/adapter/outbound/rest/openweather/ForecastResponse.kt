package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.domain.model.weather.*
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ForecastResponse(
    val dt: Int = 0,
    val dtText: String = "",
    val main: ForecastDataMain = ForecastDataMain(),
    val weather: List<WeatherConditionResponse> = listOf(),
    val clouds: ForecastDataClouds = ForecastDataClouds(),
    val wind: ForecastDataWind = ForecastDataWind(),
    val city: CityResponse = CityResponse(),
    val sys: ForecastDataSys = ForecastDataSys()
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class ForecastDataMain(
        val temp: Float = 0f,
        val feelsLike: Float = 0f,
        val tempMin: Float = 0f,
        val tempMax: Float = 0f,
        val pressure: Int = 0,
        val seaLevel: Float = 0f,
        val grndLevel: Float = 0f,
        val humidity: Float = 0f,
        val tempKf: Float = 0f
    )

    data class ForecastDataClouds(
        val all: Int = 0
    )

    data class ForecastDataWind(
        val speed: Float = 0f,
        val deg: Int = 0
    )

    data class ForecastDataSys(
        val pod: String = ""
    )
}
