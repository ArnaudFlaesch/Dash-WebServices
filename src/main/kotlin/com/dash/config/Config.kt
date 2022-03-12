package com.dash.config

import com.dash.controller.ErrorHandler
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.web.client.RestTemplate

@Configuration
class Config {

    @Bean
    fun restTemplate(builder: RestTemplateBuilder): RestTemplate {
        val factory = SimpleClientHttpRequestFactory()
        factory.setConnectTimeout(3000)
        factory.setReadTimeout(3000)
        val restTemplate = RestTemplate(factory)
        restTemplate.errorHandler = ErrorHandler()
        return restTemplate
    }
}