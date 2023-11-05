package com.dash.infra.rest.config

import com.dash.app.controller.ErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

@Configuration
class Config {
    companion object {
        const val TIMEOUT = 3000
    }

    @Bean
    fun restTemplate(): RestTemplate {
        val factory = SimpleClientHttpRequestFactory()
        factory.setConnectTimeout(TIMEOUT)
        factory.setReadTimeout(TIMEOUT)
        val restTemplate = RestTemplate(factory)
        restTemplate.messageConverters
            .add(0, StringHttpMessageConverter(StandardCharsets.UTF_8))
        restTemplate.errorHandler = ErrorHandler()
        return restTemplate
    }
}
