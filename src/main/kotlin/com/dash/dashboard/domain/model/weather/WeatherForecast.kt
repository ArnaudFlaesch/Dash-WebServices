package com.dash.dashboard.domain.model.weather

data class WeatherForecast(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)
