package com.dash.infra.rest

import com.dash.app.controller.ErrorHandler
import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.net.URI
import kotlin.reflect.KClass

@Service
class RestClient(
    private val restTemplate: RestTemplate
) {
    private val logger = LoggerFactory.getLogger(this::class.java.name)

    @Throws(RestClientException::class)
    fun <T : Any> getDataFromProxy(
        url: String,
        responseClass: KClass<T>,
        httpEntity: HttpEntity<T>? = null
    ): T {
        logger.info("Send GET request to url : $url")
        return restTemplate
            .exchange(
                URI.create(url),
                HttpMethod.GET,
                httpEntity,
                responseClass.java
            ).body
            ?: throw ErrorHandler.Companion.NotFoundException()
    }

    @Throws(RestClientException::class)
    fun <T : Any> getDataFromProxy(
        url: String,
        httpEntity: HttpEntity<T>,
        responseClass: ParameterizedTypeReference<T>
    ): T {
        logger.info("Send GET request to url : $url")
        return restTemplate
            .exchange(
                URI.create(url),
                HttpMethod.GET,
                httpEntity,
                responseClass
            ).body
            ?: throw ErrorHandler.Companion.NotFoundException()
    }

    @Throws(RestClientException::class)
    fun <T : Any> postDataFromProxy(
        url: String,
        data: Any,
        expectedResponseType: KClass<T>
    ): T {
        logger.info("Send POST request to url : $url")
        return restTemplate.postForObject(url, data, expectedResponseType.java)
            ?: throw ErrorHandler.Companion.NotFoundException()
    }
}
