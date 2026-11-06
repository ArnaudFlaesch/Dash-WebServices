package com.dash.infra.rest

import org.springframework.boot.context.properties.ConfigurationProperties

/**
 * Credentials of the external APIs. Their base URLs (and the AirParif API key, sent as a default header)
 * are configured on the HTTP service groups, under `spring.http.serviceclient`.
 */
@ConfigurationProperties("dash.api")
data class ApiProperties(
    val openweather: OpenWeather,
    val steam: Steam,
    val strava: Strava
) {
    data class OpenWeather(
        val key: String
    )

    data class Steam(
        val key: String
    )

    data class Strava(
        val clientId: String,
        val clientSecret: String
    )
}
