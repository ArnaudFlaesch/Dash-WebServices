package com.common.app.security.response

data class JwtResponse(
    val accessToken: String,
    val id: Int,
    val username: String,
    val email: String,
    val roles: List<String>,
    val tokenType: String = "Bearer"
)
