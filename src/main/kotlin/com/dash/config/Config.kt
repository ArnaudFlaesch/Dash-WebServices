package com.dash.config

import com.dash.controller.ErrorHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.SimpleClientHttpRequestFactory
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.web.client.RestTemplate
import java.nio.charset.StandardCharsets

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
        restTemplate.messageConverters
            .add(0, StringHttpMessageConverter(StandardCharsets.UTF_8));
        restTemplate.errorHandler = ErrorHandler()
        return restTemplate
    }
}
