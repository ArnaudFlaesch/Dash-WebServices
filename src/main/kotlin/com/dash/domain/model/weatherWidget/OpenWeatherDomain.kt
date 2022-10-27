package com.dash.domain.model.weatherWidget

data class OpenWeatherForecastDomain(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<ForecastDomain>,
    val city: CityDomain
)

data class OpenWeatherWeatherDomain(
    val coord: LatLngDomain,
    val weather: List<WeatherDomain>,
    val base: String,
    val main: WeatherMainDomain,
    val visibility: Int,
    val wind: WeatherWindDomain,
    val clouds: WeatherCloudDomain,
    val dt: Int,
    val sys: WeatherSysDomain,
    val timezone: Int,
    val id: Int,
    val name: String,
    val cod: Int
) {
    data class WeatherMainDomain(
        val temp: Int,
        val feelsLike: Int,
        val tempMin: Int,
        val tempMax: Int,
        val pressure: Int,
        val humidity: Int
    )

    data class WeatherWindDomain(
        val speed: Int,
        val deg: Int,
        val gust: Int
    )

    data class WeatherSysDomain(
        val id: Int,
        val type: Int,
        val country: String,
        val sunrise: Int,
        val sunset: Int
    )

    data class WeatherCloudDomain(
        val all: String
    )
}

data class ForecastDomain(
    val dt: Int,
    val dtText: String,
    val main: ForecastDataMainDomain,
    val weather: List<WeatherDomain>,
    val clouds: ForecastDataCloudsDomain,
    val wind: ForecastDataWindDomain,
    val city: CityDomain,
    val sys: ForecastDataSysDomain
) {
    data class ForecastDataMainDomain(
        val temp: Int,
        val feelsLike: Int,
        val tempMin: Int,
        val tempMax: Int,
        val pressure: Int,
        val seaLevel: Int,
        val grndLevel: Int,
        val humidity: Int,
        val tempKf: Int
    )

    data class ForecastDataCloudsDomain(
        val all: Int
    )

    data class ForecastDataWindDomain(
        val speed: Int,
        val deg: Int
    )

    data class ForecastDataSysDomain(
        val pod: String
    )
}

data class CityDomain(
    val id: Int,
    val name: String,
    val coord: LatLngDomain,
    val country: String,
    val population: Int,
    val timezone: Int,
    val sunrise: Int,
    val sunset: Int
)

data class LatLngDomain(
    val lat: Int,
    val lon: Int
)

data class WeatherDomain(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
