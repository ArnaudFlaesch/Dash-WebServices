package com.dash.dashboard.adapter.outbound.rest.openweather

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.service.annotation.GetExchange
import org.springframework.web.service.annotation.HttpExchange

// Declarative HTTP client, registered in RestClientConfig. Its base URL and default headers come from
// spring.http.serviceclient.openweather in application.yml. A null return value means an empty response body.

@HttpExchange(accept = [MediaType.APPLICATION_JSON_VALUE])
interface OpenWeatherApi {
    @GetExchange("/weather")
    fun getWeather(
        @RequestParam("units") units: String,
        @RequestParam("lang") lang: String,
        @RequestParam("appid") apiKey: String,
        @RequestParam("q") city: String
    ): OpenWeatherWeatherResponse?

    @GetExchange("/forecast")
    fun getForecast(
        @RequestParam("units") units: String,
        @RequestParam("lang") lang: String,
        @RequestParam("appid") apiKey: String,
        @RequestParam("q") city: String
    ): OpenWeatherForecastResponse?
}
