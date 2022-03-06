package com.dash.service

import com.dash.controller.ErrorHandler
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

@Service
class ProxyService {

    fun getDataFromProxy(url: String): String {
        val client = HttpClient.newHttpClient()

        val request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .build()
        return client.send(request, HttpResponse.BodyHandlers.ofString()).body()
    }

    @Throws(RestClientException::class)
    fun postDataFromProxy(url: String, data: Any): String? {
        val restTemplate = RestTemplate()
        restTemplate.errorHandler = ErrorHandler()
        return restTemplate.postForObject(url, data, String::class.java)
    }
}
