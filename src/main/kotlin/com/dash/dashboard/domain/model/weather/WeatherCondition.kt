package com.dash.dashboard.domain.model.weather

data class WeatherCondition(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
