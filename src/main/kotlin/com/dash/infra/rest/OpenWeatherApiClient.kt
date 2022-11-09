package com.dash.infra.rest

import com.dash.infra.apimodel.openweather.OpenWeatherForecastResponse
import com.dash.infra.apimodel.openweather.OpenWeatherWeatherResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenWeatherApiClient {

    @Autowired
    private lateinit var restClient: RestClient

    @Value("\${dash.app.OPENWEATHERMAP_API_URL}")
    private lateinit var weatherApiUrl: String

    @Value("\${dash.app.OPENWEATHERMAP_KEY}")
    private lateinit var openWeatherMapKey: String

    companion object {
        private const val weatherEndpoint = "/weather"
        private const val forecastEndpoint = "/forecast"
        private const val apiOptions = "?units=metric&lang=fr&appid="
    }

    fun getWeatherData(city: String): OpenWeatherWeatherResponse? {
        val url = "$weatherApiUrl$weatherEndpoint$apiOptions$openWeatherMapKey&q=$city"
        return restClient.getDataFromProxy(url, OpenWeatherWeatherResponse::class)
    }

    fun getForecastData(city: String): OpenWeatherForecastResponse? {
        val url = "$weatherApiUrl$forecastEndpoint$apiOptions$openWeatherMapKey&q=$city"
        return restClient.getDataFromProxy(url, OpenWeatherForecastResponse::class)
    }
}
