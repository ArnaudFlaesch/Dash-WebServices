package com.dash.dashboard.domain.model.weather

data class City(
    val id: Int,
    val name: String,
    val coord: LatLng,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)
