package com.dash.infra.adapter

import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import com.dash.infra.rest.OpenWeatherApiClient
import org.springframework.stereotype.Component

@Component
class WeatherWidgetAdapter(
    private val openWeatherApiClient: OpenWeatherApiClient
) {
    fun getWeatherData(city: String): OpenWeatherWeatherDomain {
        val weatherDataResponse = openWeatherApiClient.getWeatherData(city)
        return weatherDataResponse.toDomain()
    }

    fun getForecastData(city: String): OpenWeatherForecastDomain {
        val forecastDataResponse = openWeatherApiClient.getForecastData(city)
        return forecastDataResponse.toDomain()
    }
}
