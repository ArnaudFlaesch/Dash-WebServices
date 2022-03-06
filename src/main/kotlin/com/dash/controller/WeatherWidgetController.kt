package com.dash.controller

import com.dash.service.ProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/weatherWidget")
class WeatherWidgetController {

    @Autowired
    private lateinit var proxyService: ProxyService

    companion object {
        @Value("\${dash.app.OPENWEATHERMAP_KEY}")
        private val OPENWEATHERMAP_KEY: String? = null

        const val WEATHER_API = "https://api.openweathermap.org/data/2.5/"
        const val WEATHER_ENDPOINT = "weather"
        const val FORECAST_ENDPOINT = "forecast"
        const val API_OPTIONS = "?units=metric&lang=fr&appid="
    }

    @GetMapping("/weather")
    fun getWeatherData(@RequestParam(value = "city") city: String): Any? {
        val url = "$WEATHER_API$WEATHER_ENDPOINT$API_OPTIONS$OPENWEATHERMAP_KEY&q=$city"
        return proxyService.getDataFromProxy(url)
    }

    @GetMapping("/forecast")
    fun getForecastData(@RequestParam(value = "city") city: String): Any? {
        val url = "$WEATHER_API$FORECAST_ENDPOINT$API_OPTIONS$OPENWEATHERMAP_KEY&q=$city"
        return proxyService.getDataFromProxy(url)
    }
}
