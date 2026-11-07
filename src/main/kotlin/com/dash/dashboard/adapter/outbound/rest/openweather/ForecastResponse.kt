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
    fun toDomain(): Forecast =
        Forecast(
            dt = this.dt,
            dtText = this.dtText,
            main = this.main.toDomain(),
            weather = this.weather.map(WeatherConditionResponse::toDomain),
            clouds = this.clouds.toDomain(),
            wind = this.wind.toDomain(),
            city = this.city.toDomain(),
            sys = this.sys.toDomain()
        )

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
    ) {
        fun toDomain(): Forecast.ForecastDataMain =
            Forecast.ForecastDataMain(
                temp = this.temp,
                feelsLike = this.feelsLike,
                tempMin = this.tempMin,
                tempMax = this.tempMax,
                pressure = this.pressure,
                seaLevel = this.seaLevel,
                grndLevel = this.grndLevel,
                humidity = this.humidity,
                tempKf = this.tempKf
            )
    }

    data class ForecastDataClouds(
        val all: Int = 0
    ) {
        fun toDomain(): Forecast.ForecastDataClouds = Forecast.ForecastDataClouds(all = this.all)
    }

    data class ForecastDataWind(
        val speed: Float = 0f,
        val deg: Int = 0
    ) {
        fun toDomain(): Forecast.ForecastDataWind =
            Forecast.ForecastDataWind(
                speed = this.speed,
                deg = this.deg
            )
    }

    data class ForecastDataSys(
        val pod: String = ""
    ) {
        fun toDomain(): Forecast.ForecastDataSys = Forecast.ForecastDataSys(pod = this.pod)
    }
}
