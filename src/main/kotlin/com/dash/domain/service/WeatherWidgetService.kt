package com.dash.domain.service

import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import com.dash.infra.adapter.WeatherWidgetAdapter
import org.springframework.stereotype.Service

@Service
class WeatherWidgetService {

    private val weatherWidgetAdapter: WeatherWidgetAdapter

    fun getWeatherData(city: String): OpenWeatherWeatherDomain = weatherWidgetAdapter.getWeatherData(city)

    fun getForecastData(city: String): OpenWeatherForecastDomain = weatherWidgetAdapter.getForecastData(city)
}
