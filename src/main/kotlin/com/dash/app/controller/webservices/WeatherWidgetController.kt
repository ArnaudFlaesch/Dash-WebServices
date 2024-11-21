package com.dash.app.controller.webservices

import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import com.dash.domain.service.WeatherWidgetService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/weatherWidget")
class WeatherWidgetController(
    private val weatherWidgetService: WeatherWidgetService
) {
    @GetMapping("/weather")
    fun getWeatherData(
        @RequestParam(value = "city") city: String
    ): OpenWeatherWeatherDomain = weatherWidgetService.getWeatherData(city)

    @GetMapping("/forecast")
    fun getForecastData(
        @RequestParam(value = "city") city: String
    ): OpenWeatherForecastDomain = weatherWidgetService.getForecastData(city)
}
