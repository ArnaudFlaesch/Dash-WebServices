package com.dash.infra.apimodel.openweather

import com.dash.domain.model.weatherWidget.*
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

data class OpenWeatherForecastResponse(
    val cod: String = "",
    val message: Int = 0,
    val cnt: Int = 0,
    val list: List<ForecastResponse> = listOf(),
    val city: CityResponse = CityResponse()
) {
    fun toDomain(): OpenWeatherForecastDomain =
        OpenWeatherForecastDomain(
            cod = this.cod,
            message = this.message,
            cnt = this.cnt,
            list = this.list.map(ForecastResponse::toDomain),
            city = this.city.toDomain()
        )
}

data class OpenWeatherWeatherResponse(
    val coord: LatLng = LatLng(),
    val weather: List<Weather> = listOf(),
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
    fun toDomain(): OpenWeatherWeatherDomain =
        OpenWeatherWeatherDomain(
            coord = this.coord.toDomain(),
            weather = this.weather.map(Weather::toDomain),
            base = this.base,
            main = this.main.toDomain(),
            visibility = this.visibility,
            wind = this.wind.toDomain(),
            clouds = this.clouds.toDomain(),
            dt = this.dt,
            sys = this.sys.toDomain(),
            timezone = this.timezone,
            id = this.id,
            name = this.name,
            cod = this.cod
        )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class WeatherMain(
        val temp: Float = 0f,
        val feelsLike: Float = 0f,
        val tempMin: Float = 0f,
        val tempMax: Float = 0f,
        val pressure: Int = 0,
        val humidity: Float = 0f
    ) {
        fun toDomain(): OpenWeatherWeatherDomain.WeatherMainDomain =
            OpenWeatherWeatherDomain.WeatherMainDomain(
                temp = this.temp,
                feelsLike = this.feelsLike,
                tempMin = this.tempMin,
                tempMax = this.tempMax,
                pressure = this.pressure,
                humidity = this.humidity
            )
    }

    data class WeatherWind(
        val speed: Int = 0,
        val deg: Int = 0,
        val gust: Int = 0
    ) {
        fun toDomain(): OpenWeatherWeatherDomain.WeatherWindDomain =
            OpenWeatherWeatherDomain.WeatherWindDomain(
                speed = this.speed,
                deg = this.deg,
                gust = this.gust
            )
    }

    data class WeatherSys(
        val id: Int = 0,
        val type: Int = 0,
        val country: String = "",
        val sunrise: Int = 0,
        val sunset: Int = 0
    ) {
        fun toDomain(): OpenWeatherWeatherDomain.WeatherSysDomain =
            OpenWeatherWeatherDomain.WeatherSysDomain(
                id = this.id,
                type = this.type,
                country = this.country,
                sunrise = this.sunrise,
                sunset = this.sunset
            )
    }

    data class WeatherCloud(
        val all: String = ""
    ) {
        fun toDomain(): OpenWeatherWeatherDomain.WeatherCloudDomain =
            OpenWeatherWeatherDomain.WeatherCloudDomain(all = this.all)
    }
}

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
data class ForecastResponse(
    val dt: Int = 0,
    val dtText: String = "",
    val main: ForecastDataMain = ForecastDataMain(),
    val weather: List<Weather> = listOf(),
    val clouds: ForecastDataClouds = ForecastDataClouds(),
    val wind: ForecastDataWind = ForecastDataWind(),
    val city: CityResponse = CityResponse(),
    val sys: ForecastDataSys = ForecastDataSys()
) {
    fun toDomain(): ForecastDomain =
        ForecastDomain(
            dt = this.dt,
            dtText = this.dtText,
            main = this.main.toDomain(),
            weather = this.weather.map(Weather::toDomain),
            clouds = this.clouds.toDomain(),
            wind = this.wind.toDomain(),
            city = this.city.toDomain(),
            sys = this.sys.toDomain()
        )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class ForecastDataMain(
        val temp: Int = 0,
        val feelsLike: Float = 0f,
        val tempMin: Float = 0f,
        val tempMax: Float = 0f,
        val pressure: Int = 0,
        val seaLevel: Float = 0f,
        val grndLevel: Float = 0f,
        val humidity: Float = 0f,
        val tempKf: Float = 0f
    ) {
        fun toDomain(): ForecastDomain.ForecastDataMainDomain =
            ForecastDomain.ForecastDataMainDomain(
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
        fun toDomain(): ForecastDomain.ForecastDataCloudsDomain =
            ForecastDomain.ForecastDataCloudsDomain(all = this.all)
    }

    data class ForecastDataWind(
        val speed: Int = 0,
        val deg: Int = 0
    ) {
        fun toDomain(): ForecastDomain.ForecastDataWindDomain =
            ForecastDomain.ForecastDataWindDomain(
                speed = this.speed,
                deg = this.deg
            )
    }

    data class ForecastDataSys(
        val pod: String = ""
    ) {
        fun toDomain(): ForecastDomain.ForecastDataSysDomain = ForecastDomain.ForecastDataSysDomain(pod = this.pod)
    }
}

data class CityResponse(
    val id: Int = 0,
    val name: String = "",
    val coord: LatLng = LatLng(),
    val country: String = "",
    val population: Int = 0,
    val timezone: Int = 0,
    val sunrise: Int = 0,
    val sunset: Int = 0
) {
    fun toDomain(): CityDomain =
        CityDomain(
            id = this.id,
            name = this.name,
            coord = this.coord.toDomain(),
            country = this.country,
            population = this.population,
            timezone = this.timezone,
            sunrise = this.sunrise,
            sunset = this.sunset
        )
}

data class LatLng(
    val lat: Int = 0,
    val lon: Int = 0
) {
    fun toDomain(): LatLngDomain = LatLngDomain(lat = this.lat, lon = this.lon)
}

data class Weather(
    val id: Int = 0,
    val main: String = "",
    val description: String = "",
    val icon: String = ""
) {
    fun toDomain(): WeatherDomain =
        WeatherDomain(
            id = this.id,
            main = this.main,
            description = this.description,
            icon = this.icon
        )
}
