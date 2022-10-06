package com.dash.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class ProxyService {

    @Autowired
    lateinit var restTemplate: RestTemplate

    @Throws(RestClientException::class)
    fun getDataFromProxy(url: String): String? =
        restTemplate.getForObject(URI.create(url), String::class.java)

    @Throws(RestClientException::class)
    final inline fun <reified T> getDataFromProxy(url: String, headers: HttpEntity<T>): ResponseEntity<T> =
        restTemplate.exchange(URI.create(url), HttpMethod.GET, headers, T::class.java)

    @Throws(RestClientException::class)
    fun postDataFromProxy(url: String, data: Any): String? = restTemplate.postForObject(url, data, String::class.java)
}
