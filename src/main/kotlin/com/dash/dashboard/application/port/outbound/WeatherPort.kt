package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.weather.CurrentWeather
import com.dash.dashboard.domain.model.weather.WeatherForecast

interface WeatherPort {
    fun getWeatherData(city: String): CurrentWeather

    fun getForecastData(city: String): WeatherForecast
}
