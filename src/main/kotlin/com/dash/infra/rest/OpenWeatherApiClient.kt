package com.dash.infra.rest

import com.dash.infra.apimodel.openweather.OpenWeatherForecastResponse
import com.dash.infra.apimodel.openweather.OpenWeatherWeatherResponse
import com.dash.infra.rest.api.OpenWeatherApi
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class OpenWeatherApiClient(
    private val openWeatherApi: OpenWeatherApi,
    @Value("\${dash.app.OPENWEATHERMAP_KEY}")
    private val openWeatherMapKey: String
) {
    companion object {
        private const val UNITS = "metric"
        private const val LANG = "fr"
    }

    fun getWeatherData(city: String): OpenWeatherWeatherResponse =
        openWeatherApi.getWeather(UNITS, LANG, openWeatherMapKey, city).orNotFound()

    fun getForecastData(city: String): OpenWeatherForecastResponse =
        openWeatherApi.getForecast(UNITS, LANG, openWeatherMapKey, city).orNotFound()
}
