package com.common.app.security.response

class JwtResponse(
    var accessToken: String,
    var id: Int?,
    var username: String?,
    var email: String?,
    val roles: List<String>
) {
    var tokenType = "Bearer"
}
