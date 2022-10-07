package com.dash.infra.rest

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.net.URI
import kotlin.reflect.KClass

@Service
class RestClient {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @Throws(RestClientException::class)
    fun <T : Any> getDataFromProxy(url: String, responseClass: KClass<T>, httpEntity: HttpEntity<T>? = null): T? {
        logger.info("Send GET request to url : $url")
        return restTemplate.exchange(URI.create(url), HttpMethod.GET, httpEntity, responseClass.java).body
    }

    @Throws(RestClientException::class)
    fun postDataFromProxy(url: String, data: Any): String? = restTemplate.postForObject(url, data, String::class.java)
}
