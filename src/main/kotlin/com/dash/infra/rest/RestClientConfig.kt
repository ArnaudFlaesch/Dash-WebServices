package com.dash.infra.rest

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException

@Configuration
class RestClientConfig {
    @Bean
    fun restClient(builder: RestClient.Builder): RestClient =
        builder
            .defaultStatusHandler(HttpStatusCode::isError) { _, response ->
                throw when (response.statusCode) {
                    HttpStatus.BAD_REQUEST -> BadRequestException()
                    HttpStatus.NOT_FOUND -> NotFoundException()
                    HttpStatus.INTERNAL_SERVER_ERROR -> InternalServerErrorException()
                    else -> RestClientException(response.statusCode.toString())
                }
            }.build()
}
