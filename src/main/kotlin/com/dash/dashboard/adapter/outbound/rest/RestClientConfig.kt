package com.dash.dashboard.adapter.outbound.rest

import com.dash.dashboard.adapter.outbound.rest.airparif.AirParifApi
import com.dash.dashboard.adapter.outbound.rest.openweather.OpenWeatherApi
import com.dash.dashboard.adapter.outbound.rest.steam.SteamApi
import com.dash.dashboard.adapter.outbound.rest.strava.StravaApi
import org.springframework.boot.restclient.RestClientCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException
import org.springframework.web.service.registry.ImportHttpServices

@Configuration
@ImportHttpServices(group = "airparif", types = [AirParifApi::class])
@ImportHttpServices(group = "openweather", types = [OpenWeatherApi::class])
@ImportHttpServices(group = "steam", types = [SteamApi::class])
@ImportHttpServices(group = "strava", types = [StravaApi::class])
class RestClientConfig {
    /** Applied by Spring Boot to every RestClient builder, including the HTTP service groups. */
    @Bean
    fun errorStatusHandlerCustomizer() =
        RestClientCustomizer { builder ->
            builder.defaultStatusHandler(HttpStatusCode::isError) { _, response ->
                throw when (response.statusCode) {
                    HttpStatus.BAD_REQUEST -> BadRequestException()
                    HttpStatus.NOT_FOUND -> NotFoundException()
                    HttpStatus.INTERNAL_SERVER_ERROR -> InternalServerErrorException()
                    else -> RestClientException(response.statusCode.toString())
                }
            }
        }

    @Bean
    fun restClient(builder: RestClient.Builder): RestClient = builder.build()
}
