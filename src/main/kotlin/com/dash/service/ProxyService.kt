package com.dash.service

import org.springframework.stereotype.Service
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class ProxyService {
    val WEATHER_API = "https://api.openweathermap.org/data/2.5/";
    val WEATHER_ENDPOINT = "weather";
    val FORECAST_ENDPOINT = "forecast";
    val API_OPTIONS = "?units=metric&lang=fr&appid=";


    fun getDataFromProxy(url: String): String {
        val client = HttpClient.newHttpClient()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body()
    }
}