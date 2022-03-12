package com.dash.config

import com.dash.controller.ErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class Config {

    companion object {
        const val timeout = 3000
    }

    @Bean
    fun restTemplate(): RestTemplate {
        val factory = SimpleClientHttpRequestFactory()
        factory.setConnectTimeout(timeout)
        factory.setReadTimeout(timeout)
        val restTemplate = RestTemplate(factory)
        restTemplate.errorHandler = ErrorHandler()
        return restTemplate
    }
}
