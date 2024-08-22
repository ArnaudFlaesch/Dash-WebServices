package com.dash.infra.apimodel.openweather

import com.dash.domain.model.weatherWidget.*
import com.fasterxml.jackson.annotation.JsonProperty

data class OpenWeatherForecastResponse(
    @JsonProperty("cod")
    val cod: String = "",
    @JsonProperty("message")
    val message: Int = 0,
    @JsonProperty("cnt")
    val cnt: Int = 0,
    @JsonProperty("list")
    val list: List<ForecastResponse> = listOf(),
    @JsonProperty("city")
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
    @JsonProperty("coord")
    val coord: LatLng = LatLng(),
    @JsonProperty("weather")
    val weather: List<Weather> = listOf(),
    @JsonProperty("base")
    val base: String = "",
    @JsonProperty("main")
    val main: WeatherMain = WeatherMain(),
    @JsonProperty("visibility")
    val visibility: Int = 0,
    @JsonProperty("wind")
    val wind: WeatherWind = WeatherWind(),
    @JsonProperty("clouds")
    val clouds: WeatherCloud = WeatherCloud(),
    @JsonProperty("dt")
    val dt: Int = 0,
    @JsonProperty("sys")
    val sys: WeatherSys = WeatherSys(),
    @JsonProperty("timezone")
    val timezone: Int = 0,
    @JsonProperty("id")
    val id: Int = 0,
    @JsonProperty("name")
    val name: String = "",
    @JsonProperty("cod")
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

    data class WeatherMain(
        @JsonProperty("temp")
        val temp: Float = 0f,
        @JsonProperty("feels_like")
        val feelsLike: Float = 0f,
        @JsonProperty("temp_min")
        val tempMin: Float = 0f,
        @JsonProperty("temp_max")
        val tempMax: Float = 0f,
        @JsonProperty("pressure")
        val pressure: Int = 0,
        @JsonProperty("humidity")
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
        @JsonProperty("speed")
        val speed: Int = 0,
        @JsonProperty("deg")
        val deg: Int = 0,
        @JsonProperty("gust")
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
        @JsonProperty("id")
        val id: Int = 0,
        @JsonProperty("type")
        val type: Int = 0,
        @JsonProperty("country")
        val country: String = "",
        @JsonProperty("sunrise")
        val sunrise: Int = 0,
        @JsonProperty("sunset")
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
        @JsonProperty("all")
        val all: String = ""
    ) {
        fun toDomain(): OpenWeatherWeatherDomain.WeatherCloudDomain =
            OpenWeatherWeatherDomain.WeatherCloudDomain(all = this.all)
    }
}

data class ForecastResponse(
    @JsonProperty("dt")
    val dt: Int = 0,
    @JsonProperty("dt_text")
    val dtText: String = "",
    @JsonProperty("main")
    val main: ForecastDataMain = ForecastDataMain(),
    @JsonProperty("weather")
    val weather: List<Weather> = listOf(),
    @JsonProperty("clouds")
    val clouds: ForecastDataClouds = ForecastDataClouds(),
    @JsonProperty("wind")
    val wind: ForecastDataWind = ForecastDataWind(),
    @JsonProperty("city")
    val city: CityResponse = CityResponse(),
    @JsonProperty("sys")
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

    data class ForecastDataMain(
        @JsonProperty("temp")
        val temp: Int = 0,
        @JsonProperty("feels_like")
        val feelsLike: Float = 0f,
        @JsonProperty("temp_min")
        val tempMin: Float = 0f,
        @JsonProperty("temp_max")
        val tempMax: Float = 0f,
        @JsonProperty("pressure")
        val pressure: Int = 0,
        @JsonProperty("sea_level")
        val seaLevel: Float = 0f,
        @JsonProperty("grnd_level")
        val grndLevel: Float = 0f,
        @JsonProperty("humidity")
        val humidity: Float = 0f,
        @JsonProperty("temp_kf")
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
        @JsonProperty("all")
        val all: Int = 0
    ) {
        fun toDomain(): ForecastDomain.ForecastDataCloudsDomain =
            ForecastDomain.ForecastDataCloudsDomain(all = this.all)
    }

    data class ForecastDataWind(
        @JsonProperty("speed")
        val speed: Int = 0,
        @JsonProperty("deg")
        val deg: Int = 0
    ) {
        fun toDomain(): ForecastDomain.ForecastDataWindDomain =
            ForecastDomain.ForecastDataWindDomain(
                speed = this.speed,
                deg = this.deg
            )
    }

    data class ForecastDataSys(
        @JsonProperty("pod")
        val pod: String = ""
    ) {
        fun toDomain(): ForecastDomain.ForecastDataSysDomain = ForecastDomain.ForecastDataSysDomain(pod = this.pod)
    }
}

data class CityResponse(
    @JsonProperty("id")
    val id: Int = 0,
    @JsonProperty("name")
    val name: String = "",
    @JsonProperty("coord")
    val coord: LatLng = LatLng(),
    @JsonProperty("country")
    val country: String = "",
    @JsonProperty("population")
    val population: Int = 0,
    @JsonProperty("timezone")
    val timezone: Int = 0,
    @JsonProperty("sunrise")
    val sunrise: Int = 0,
    @JsonProperty("sunset")
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
    @JsonProperty("lat")
    val lat: Int = 0,
    @JsonProperty("lon")
    val lon: Int = 0
) {
    fun toDomain(): LatLngDomain = LatLngDomain(lat = this.lat, lon = this.lon)
}

data class Weather(
    @JsonProperty("id")
    val id: Int = 0,
    @JsonProperty("main")
    val main: String = "",
    @JsonProperty("description")
    val description: String = "",
    @JsonProperty("icon")
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
