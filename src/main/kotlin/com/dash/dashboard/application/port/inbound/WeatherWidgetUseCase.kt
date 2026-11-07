package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.weather.CurrentWeather
import com.dash.dashboard.domain.model.weather.WeatherForecast

interface WeatherWidgetUseCase {
    fun getWeatherData(city: String): CurrentWeather

    fun getForecastData(city: String): WeatherForecast
}
