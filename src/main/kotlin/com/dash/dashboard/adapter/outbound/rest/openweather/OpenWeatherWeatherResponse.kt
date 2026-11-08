package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.domain.model.weather.*
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

data class OpenWeatherWeatherResponse(
    val coord: LatLngResponse = LatLngResponse(),
    val weather: List<WeatherConditionResponse> = listOf(),
    val base: String = "",
    val main: WeatherMain = WeatherMain(),
    val visibility: Int = 0,
    val wind: WeatherWind = WeatherWind(),
    val clouds: WeatherCloud = WeatherCloud(),
    val dt: Int = 0,
    val sys: WeatherSys = WeatherSys(),
    val timezone: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val cod: Int = 0
) {
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class WeatherMain(
        val temp: Float = 0f,
        val feelsLike: Float = 0f,
        val tempMin: Float = 0f,
        val tempMax: Float = 0f,
        val pressure: Int = 0,
        val humidity: Float = 0f
    )

    data class WeatherWind(
        val speed: Float = 0f,
        val deg: Int = 0,
        val gust: Float = 0f
    )

    data class WeatherSys(
        val id: Int = 0,
        val type: Int = 0,
        val country: String = "",
        val sunrise: Int = 0,
        val sunset: Int = 0
    )

    data class WeatherCloud(
        val all: String = ""
    )
}
