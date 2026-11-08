package com.dash.shared.adapter.security

import org.springframework.boot.context.properties.ConfigurationProperties
import java.time.Duration

@ConfigurationProperties("dash.security")
data class SecurityProperties(
    val allowedOrigins: List<String>,
    val jwt: Jwt
) {
    data class Jwt(
        /** Base64-encoded HMAC key used to sign the tokens. */
        val secret: String,
        val expiration: Duration
    )
}
