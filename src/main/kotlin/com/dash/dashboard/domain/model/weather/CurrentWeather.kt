package com.dash.dashboard.domain.model.weather

data class CurrentWeather(
    val coord: LatLng,
    val weather: List<WeatherCondition>,
    val base: String,
    val main: WeatherMain,
    val visibility: Int,
    val wind: WeatherWind,
    val clouds: WeatherCloud,
    val dt: Int,
    val sys: WeatherSys,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {
    data class WeatherMain(
        val temp: Float,
        val feelsLike: Float,
        val tempMin: Float,
        val tempMax: Float,
        val pressure: Int,
        val humidity: Float
    )

    data class WeatherWind(
        val speed: Float,
        val deg: Int,
        val gust: Float
    )

    data class WeatherSys(
        val id: Int,
        val type: Int,
        val country: String,
        val sunrise: Int,
        val sunset: Int
    )

    data class WeatherCloud(
        val all: String
    )
}
