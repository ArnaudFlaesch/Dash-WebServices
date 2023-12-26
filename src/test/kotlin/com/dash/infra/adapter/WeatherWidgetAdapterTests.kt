package com.dash.infra.adapter

import com.dash.infra.apimodel.openweather.*
import com.dash.infra.rest.OpenWeatherApiClient
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class WeatherWidgetAdapterTests {
    @Mock
    private lateinit var weatherApiClient: OpenWeatherApiClient

    @InjectMocks
    private lateinit var weatherWidgetAdapter: WeatherWidgetAdapter

    @Test
    fun shouldReturnWeatherData() {
        val city = "Paris"

        val weatherListResponse = listOf(Weather(icon = "sunny"), Weather(icon = "cloudy"))
        val weatherApiData = OpenWeatherWeatherResponse(name = city, id = 123, weather = weatherListResponse)

        given(weatherApiClient.getWeatherData(city)).willReturn(weatherApiData)
        val response = weatherWidgetAdapter.getWeatherData(city)
        assertEquals(2, response.weather.size)
        assertEquals(city, response.name)
    }

    @Test
    fun shouldReturnForecastData() {
        val city = "Paris"

        val cityResponse = CityResponse(name = city, country = "France")
        val forecastList = listOf(ForecastResponse(dt = 1), ForecastResponse(dt = 2))
        val forecastData = OpenWeatherForecastResponse(list = forecastList, city = cityResponse)

        given(weatherApiClient.getForecastData(city)).willReturn(forecastData)
        val response = weatherWidgetAdapter.getForecastData(city)
        assertEquals(2, response.list.size)
        assertEquals(city, response.city.name)
    }
}
