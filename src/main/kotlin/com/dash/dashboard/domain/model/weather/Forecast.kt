package com.dash.dashboard.domain.model.weather

data class Forecast(
    val dt: Int,
    val dtText: String,
    val main: ForecastDataMain,
    val weather: List<WeatherCondition>,
    val clouds: ForecastDataClouds,
    val wind: ForecastDataWind,
    val city: City,
    val sys: ForecastDataSys
) {
    data class ForecastDataMain(
        val temp: Float,
        val feelsLike: Float,
        val tempMin: Float,
        val tempMax: Float,
        val pressure: Int,
        val seaLevel: Float,
        val grndLevel: Float,
        val humidity: Float,
        val tempKf: Float
    )

    data class ForecastDataClouds(
        val all: Int
    )

    data class ForecastDataWind(
        val speed: Float,
        val deg: Int
    )

    data class ForecastDataSys(
        val pod: String
    )
}
