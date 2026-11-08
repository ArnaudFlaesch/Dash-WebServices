package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.WeatherWidgetUseCase
import com.dash.dashboard.application.port.outbound.WeatherPort
import com.dash.dashboard.domain.model.weather.CurrentWeather
import com.dash.dashboard.domain.model.weather.WeatherForecast
import org.springframework.stereotype.Service

@Service
class WeatherWidgetService(
    private val weatherPort: WeatherPort
) : WeatherWidgetUseCase {
    override fun getWeatherData(city: String): CurrentWeather = weatherPort.getWeatherData(city)

    override fun getForecastData(city: String): WeatherForecast = weatherPort.getForecastData(city)
}
