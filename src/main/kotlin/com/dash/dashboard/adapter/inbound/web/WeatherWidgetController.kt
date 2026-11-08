package com.dash.dashboard.adapter.inbound.web

import com.dash.dashboard.application.port.inbound.WeatherWidgetUseCase
import com.dash.dashboard.domain.model.weather.CurrentWeather
import com.dash.dashboard.domain.model.weather.WeatherForecast
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/weatherWidget")
class WeatherWidgetController(
    private val weatherWidgetUseCase: WeatherWidgetUseCase
) {
    @GetMapping("/weather")
    fun getWeatherData(
        @RequestParam city: String
    ): CurrentWeather = weatherWidgetUseCase.getWeatherData(city)

    @GetMapping("/forecast")
    fun getForecastData(
        @RequestParam city: String
    ): WeatherForecast = weatherWidgetUseCase.getForecastData(city)
}
