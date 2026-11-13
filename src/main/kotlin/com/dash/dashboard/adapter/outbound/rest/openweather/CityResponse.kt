package com.dash.dashboard.adapter.outbound.rest.openweather

data class CityResponse(
    val id: Int = 0,
    val name: String = "",
    val coord: LatLngResponse = LatLngResponse(),
    val country: String = "",
    val population: Int = 0,
    val timezone: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0
)
