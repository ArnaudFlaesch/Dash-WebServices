package com.dash.app.controller

import com.dash.infra.rest.RestClient
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/weatherWidget")
class WeatherWidgetController {

    @Autowired
    private lateinit var proxyService: RestClient

    @Value("\${dash.app.OPENWEATHERMAP_KEY}")
    private lateinit var openWeatherMapKey: String

    val weatherApi = "https://api.openweathermap.org/data/2.5/"
    val weatherEndpoint = "weather"
    val forecastEndpoint = "forecast"
    val apiOptions = "?units=metric&lang=fr&appid="

    @GetMapping("/weather")
    fun getWeatherData(@RequestParam(value = "city") city: String): String? {
        val url = "$weatherApi$weatherEndpoint$apiOptions$openWeatherMapKey&q=$city"
        return proxyService.getDataFromProxy(url, String::class)
    }

    @GetMapping("/forecast")
    fun getForecastData(@RequestParam(value = "city") city: String): String? {
        val url = "$weatherApi$forecastEndpoint$apiOptions$openWeatherMapKey&q=$city"
        return proxyService.getDataFromProxy(url, String::class)
    }
}
