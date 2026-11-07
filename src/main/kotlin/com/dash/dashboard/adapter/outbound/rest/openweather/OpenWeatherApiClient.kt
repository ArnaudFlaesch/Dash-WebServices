package com.dash.dashboard.adapter.outbound.rest.openweather

import com.dash.dashboard.adapter.outbound.rest.ApiProperties
import com.dash.dashboard.adapter.outbound.rest.orNotFound
import org.springframework.stereotype.Component

@Component
class OpenWeatherApiClient(
    private val openWeatherApi: OpenWeatherApi,
    apiProperties: ApiProperties
) {
    companion object {
        private const val UNITS = "metric"
        private const val LANG = "fr"
    }

    private val openWeatherMapKey = apiProperties.openweather.key

    fun getWeatherData(city: String): OpenWeatherWeatherResponse =
        openWeatherApi.getWeather(UNITS, LANG, openWeatherMapKey, city).orNotFound()

    fun getForecastData(city: String): OpenWeatherForecastResponse =
        openWeatherApi.getForecast(UNITS, LANG, openWeatherMapKey, city).orNotFound()
}
