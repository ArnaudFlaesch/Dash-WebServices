package com.dash.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate


@Service
class ProxyService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Throws(RestClientException::class)
    fun getDataFromProxy(url: String): String? = restTemplate.getForObject(url, String::class.java)

    @Throws(RestClientException::class)
    fun postDataFromProxy(url: String, data: Any): String? = restTemplate.postForObject(url, data, String::class.java)

}
