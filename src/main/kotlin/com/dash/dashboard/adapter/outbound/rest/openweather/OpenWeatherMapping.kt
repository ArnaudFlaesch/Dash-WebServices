package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.domain.model.weather.*
import tools.jackson.databind.PropertyNamingStrategies
import tools.jackson.databind.annotation.JsonNaming

fun OpenWeatherWeatherResponse.toDomain(): CurrentWeather =
    CurrentWeather(
        coord = this.coord.toDomain(),
        weather = this.weather.map(WeatherConditionResponse::toDomain),
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

fun OpenWeatherWeatherResponse.WeatherMain.toDomain(): CurrentWeather.WeatherMain =
    CurrentWeather.WeatherMain(
        temp = this.temp,
        feelsLike = this.feelsLike,
        tempMin = this.tempMin,
        tempMax = this.tempMax,
        pressure = this.pressure,
        humidity = this.humidity
    )

fun OpenWeatherWeatherResponse.WeatherWind.toDomain(): CurrentWeather.WeatherWind =
    CurrentWeather.WeatherWind(
        speed = this.speed,
        deg = this.deg,
        gust = this.gust
    )

fun OpenWeatherWeatherResponse.WeatherSys.toDomain(): CurrentWeather.WeatherSys =
    CurrentWeather.WeatherSys(
        id = this.id,
        type = this.type,
        country = this.country,
        sunrise = this.sunrise,
        sunset = this.sunset
    )

fun OpenWeatherWeatherResponse.WeatherCloud.toDomain(): CurrentWeather.WeatherCloud =
    CurrentWeather.WeatherCloud(all = this.all)

fun OpenWeatherForecastResponse.toDomain(): WeatherForecast =
    WeatherForecast(
        cod = this.cod,
        message = this.message,
        cnt = this.cnt,
        list = this.list.map(ForecastResponse::toDomain),
        city = this.city.toDomain()
    )

fun ForecastResponse.toDomain(): Forecast =
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

fun ForecastResponse.ForecastDataMain.toDomain(): Forecast.ForecastDataMain =
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

fun ForecastResponse.ForecastDataClouds.toDomain(): Forecast.ForecastDataClouds =
    Forecast.ForecastDataClouds(all = this.all)

fun ForecastResponse.ForecastDataWind.toDomain(): Forecast.ForecastDataWind =
    Forecast.ForecastDataWind(
        speed = this.speed,
        deg = this.deg
    )

fun ForecastResponse.ForecastDataSys.toDomain(): Forecast.ForecastDataSys = Forecast.ForecastDataSys(pod = this.pod)

fun CityResponse.toDomain(): City =
    City(
        id = this.id,
        name = this.name,
        coord = this.coord.toDomain(),
        country = this.country,
        population = this.population,
        timezone = this.timezone,
        sunrise = this.sunrise,
        sunset = this.sunset
    )

fun LatLngResponse.toDomain(): LatLng = LatLng(lat = this.lat, lon = this.lon)

fun WeatherConditionResponse.toDomain(): WeatherCondition =
    WeatherCondition(
        id = this.id,
        main = this.main,
        description = this.description,
        icon = this.icon
    )
