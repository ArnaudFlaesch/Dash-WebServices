package com.dash.infra.rest

import org.slf4j.LoggerFactory
import org.springframework.core.ParameterizedTypeReference
import org.springframework.stereotype.Component
import org.springframework.web.client.RestClient
import java.net.URI

inline fun <reified T : Any> typeReference() = object : ParameterizedTypeReference<T>() {}

/** Fetches arbitrary user-supplied URLs (RSS feeds, iCal calendars). */
@Component
class ProxyClient(
    private val restClient: RestClient
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun <T : Any> get(
        uri: URI,
        responseType: ParameterizedTypeReference<T>
    ): T {
        // The URL is user-supplied: strip line breaks so it cannot forge log entries
        val sanitizedUrl = uri.toString().replace('\n', '_').replace('\r', '_')
        logger.info("Send GET request to url : {}", sanitizedUrl)
        return restClient
            .get()
            .uri(uri)
            .retrieve()
            .body(responseType)
            .orNotFound()
    }
}
