package com.dash.infra.adapter

import com.dash.infra.apimodel.openweather.OpenWeatherWeatherResponse
import com.dash.infra.apimodel.openweather.Weather
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
    fun should_return_weather_data() {
        val city = "Paris"

        val weatherListResponse = listOf(Weather(icon = "sunny"), Weather(icon = "cloudy"))
        val weatherApiData = OpenWeatherWeatherResponse(name = city, id = 123, weather = weatherListResponse)

        given(weatherApiClient.getWeatherData(city)).willReturn(weatherApiData)
        val response = weatherWidgetAdapter.getWeatherData(city)
        assertEquals(2, response.weather.size)
        assertEquals(city, response.name)
    }

    @Test
    fun should_return_empty_weather_data() {
        val city = "Paris"
        given(weatherApiClient.getWeatherData(city)).willReturn(null)
        val response = weatherWidgetAdapter.getWeatherData(city)
        assertEquals(0, response.weather.size)
        assertEquals("", response.name)
    }

    @Test
    fun should_return_empty_forecast_data() {
        val city = "Paris"
        given(weatherApiClient.getForecastData(city)).willReturn(null)
        val response = weatherWidgetAdapter.getForecastData(city)
        assertEquals(0, response.list.size)
        assertEquals("", response.city.name)
    }
}
