package com.dash.dashboard.adapter.outbound.rest.openweather

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given

@ExtendWith(MockitoExtension::class)
class OpenWeatherAdapterTests {
    @Mock
    private lateinit var weatherApiClient: OpenWeatherApiClient

    @InjectMocks
    private lateinit var openWeatherAdapter: OpenWeatherAdapter

    @Test
    fun shouldReturnWeatherData() {
        val city = "Paris"

        val weatherListResponse =
            listOf(WeatherConditionResponse().copy(icon = "sunny"), WeatherConditionResponse().copy(icon = "cloudy"))
        val weatherApiData =
            OpenWeatherWeatherResponse().copy(name = city, id = 123, weather = weatherListResponse)

        given(weatherApiClient.getWeatherData(city)).willReturn(weatherApiData)
        val response = openWeatherAdapter.getWeatherData(city)
        assertEquals(2, response.weather.size)
        assertEquals(city, response.name)
    }

    @Test
    fun shouldReturnForecastData() {
        val city = "Paris"

        val cityResponse = CityResponse().copy(name = city, country = "France")
        val forecastList = listOf(ForecastResponse().copy(dt = 1), ForecastResponse(dt = 2))
        val forecastData = OpenWeatherForecastResponse().copy(list = forecastList, city = cityResponse)

        given(weatherApiClient.getForecastData(city)).willReturn(forecastData)
        val response = openWeatherAdapter.getForecastData(city)
        assertEquals(2, response.list.size)
        assertEquals(city, response.city.name)
    }
}
