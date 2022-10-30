package com.dash.domain.service

import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import com.dash.infra.adapter.WeatherWidgetAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class WeatherWidgetService {

    @Autowired
    private lateinit var weatherWidgetAdapter: WeatherWidgetAdapter

    fun getWeatherData(city: String): OpenWeatherWeatherDomain = weatherWidgetAdapter.getWeatherData(city)

    fun getForecastData(city: String): OpenWeatherForecastDomain = weatherWidgetAdapter.getForecastData(city)
}
