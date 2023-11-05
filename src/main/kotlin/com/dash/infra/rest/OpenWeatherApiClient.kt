package com.dash.infra.rest

import com.dash.infra.apimodel.openweather.OpenWeatherForecastResponse
import com.dash.infra.apimodel.openweather.OpenWeatherWeatherResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenWeatherApiClient(
    private val restClient: RestClient,
    @Value("\${dash.app.OPENWEATHERMAP_API_URL}")
    private val weatherApiUrl: String,
    @Value("\${dash.app.OPENWEATHERMAP_KEY}")
    private val openWeatherMapKey: String
) {
    companion object {
        private const val WEATHER_ENDPOINT = "/weather"
        private const val FORECAST_ENDPOINT = "/forecast"
        private const val API_OPTIONS = "?units=metric&lang=fr&appid="
    }

    fun getWeatherData(city: String): OpenWeatherWeatherResponse {
        val url = "$weatherApiUrl$WEATHER_ENDPOINT$API_OPTIONS$openWeatherMapKey&q=$city"
        return restClient.getDataFromProxy(url, OpenWeatherWeatherResponse::class)
    }

    fun getForecastData(city: String): OpenWeatherForecastResponse {
        val url = "$weatherApiUrl$FORECAST_ENDPOINT$API_OPTIONS$openWeatherMapKey&q=$city"
        return restClient.getDataFromProxy(url, OpenWeatherForecastResponse::class)
    }
}
