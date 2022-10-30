package com.dash.app.controller

import com.dash.domain.model.weatherWidget.OpenWeatherForecastDomain
import com.dash.domain.model.weatherWidget.OpenWeatherWeatherDomain
import com.dash.domain.service.WeatherWidgetService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/weatherWidget")
class WeatherWidgetController {

    @Autowired
    private lateinit var weatherWidgetService: WeatherWidgetService

    @GetMapping("/weather")
    fun getWeatherData(@RequestParam(value = "city") city: String): OpenWeatherWeatherDomain {
        return weatherWidgetService.getWeatherData(city)
    }

    @GetMapping("/forecast")
    fun getForecastData(@RequestParam(value = "city") city: String): OpenWeatherForecastDomain {
        return weatherWidgetService.getForecastData(city)
    }
}
