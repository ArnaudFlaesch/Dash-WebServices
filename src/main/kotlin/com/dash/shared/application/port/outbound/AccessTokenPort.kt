package com.dash.shared.application.port.outbound

fun interface AccessTokenPort {
    fun generateToken(username: String): String
}
