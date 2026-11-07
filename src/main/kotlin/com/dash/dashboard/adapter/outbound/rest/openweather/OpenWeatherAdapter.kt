package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.application.port.outbound.WeatherPort
import com.dash.dashboard.domain.model.weather.CurrentWeather
import com.dash.dashboard.domain.model.weather.WeatherForecast
import org.springframework.stereotype.Component

@Component
class OpenWeatherAdapter(
    private val openWeatherApiClient: OpenWeatherApiClient
) : WeatherPort {
    override fun getWeatherData(city: String): CurrentWeather = openWeatherApiClient.getWeatherData(city).toDomain()

    override fun getForecastData(city: String): WeatherForecast = openWeatherApiClient.getForecastData(city).toDomain()
}
