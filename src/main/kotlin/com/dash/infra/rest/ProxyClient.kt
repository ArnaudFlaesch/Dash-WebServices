package com.dash.infra.rest

import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI

inline fun <reified T : Any> typeReference() = object : ParameterizedTypeReference<T>() {}

fun buildUri(
    baseUrl: String,
    path: String,
    vararg queryParams: Pair<String, Any>
): URI =
    UriComponentsBuilder
        .fromUriString(baseUrl)
        .path(path)
        .apply { queryParams.forEach { (name, value) -> queryParam(name, value) } }
        .encode()
        .build()
        .toUri()

@Component
class ProxyClient(
    private val restClient: RestClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun <T : Any> get(
        uri: URI,
        responseType: ParameterizedTypeReference<T>,
        headers: (HttpHeaders) -> Unit = {}
    ): T {
        logger.info("Send GET request to url : {}", uri)
        return restClient
            .get()
            .uri(uri)
            .headers(headers)
            .retrieve()
            .body(responseType) ?: throw NotFoundException()
    }

    fun <T : Any> post(
        uri: URI,
        body: Any,
        responseType: ParameterizedTypeReference<T>
    ): T {
        logger.info("Send POST request to url : {}", uri)
        return restClient
            .post()
            .uri(uri)
            .body(body)
            .retrieve()
            .body(responseType) ?: throw NotFoundException()
    }
}
