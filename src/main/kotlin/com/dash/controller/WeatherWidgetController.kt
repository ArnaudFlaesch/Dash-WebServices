package com.dash.controller

import com.dash.service.ProxyService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/weatherWidget")
class WeatherWidgetController {

    @Autowired
    private lateinit var proxyService: ProxyService

    @Value("\${dash.app.OPENWEATHERMAP_KEY}")
    private val OPENWEATHERMAP_KEY: String? = null

    val WEATHER_API = "https://api.openweathermap.org/data/2.5/";
    val WEATHER_ENDPOINT = "weather";
    val FORECAST_ENDPOINT = "forecast";
    val API_OPTIONS = "?units=metric&lang=fr&appid=";

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
