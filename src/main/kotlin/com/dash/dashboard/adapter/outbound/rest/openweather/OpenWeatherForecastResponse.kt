package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.domain.model.weather.*

data class OpenWeatherForecastResponse(
    val cod: String = "",
    val message: Int = 0,
    val cnt: Int = 0,
    val list: List<ForecastResponse> = listOf(),
    val city: CityResponse = CityResponse()
) {
    fun toDomain(): WeatherForecast =
        WeatherForecast(
            cod = this.cod,
            message = this.message,
            cnt = this.cnt,
            list = this.list.map(ForecastResponse::toDomain),
            city = this.city.toDomain()
        )
}
