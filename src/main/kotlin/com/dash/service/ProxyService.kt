package com.dash.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class ProxyService {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    fun getDataFromProxy(url: String): String? = restTemplate.getForObject(url, String::class.java)

    fun postDataFromProxy(url: String, data: Any): String? = restTemplate.postForObject(url, data, String::class.java)
}
