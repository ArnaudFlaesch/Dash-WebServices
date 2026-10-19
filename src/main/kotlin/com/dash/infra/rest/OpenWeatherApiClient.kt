package com.dash.infra.rest

import com.dash.infra.apimodel.openweather.OpenWeatherForecastResponse
import com.dash.infra.apimodel.openweather.OpenWeatherWeatherResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.net.URI

@Component
class OpenWeatherApiClient(
    private val proxyClient: ProxyClient,
    @Value("\${dash.app.OPENWEATHERMAP_API_URL}")
    private val weatherApiUrl: String,
    @Value("\${dash.app.OPENWEATHERMAP_KEY}")
    private val openWeatherMapKey: String
) {
    companion object {
        private const val WEATHER_ENDPOINT = "/weather"
        private const val FORECAST_ENDPOINT = "/forecast"
    }

    fun getWeatherData(city: String): OpenWeatherWeatherResponse =
        proxyClient.get(buildCityUri(WEATHER_ENDPOINT, city), typeReference())

    fun getForecastData(city: String): OpenWeatherForecastResponse =
        proxyClient.get(buildCityUri(FORECAST_ENDPOINT, city), typeReference())

    private fun buildCityUri(
        endpoint: String,
        city: String
    ): URI =
        buildUri(
            weatherApiUrl,
            endpoint,
            "units" to "metric",
            "lang" to "fr",
            "appid" to openWeatherMapKey,
            "q" to city
        )
}
