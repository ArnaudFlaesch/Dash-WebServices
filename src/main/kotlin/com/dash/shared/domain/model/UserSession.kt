package com.dash.shared.domain.model

/** The session opened by a successful login: the authenticated user and the access token to send with requests. */
data class UserSession(
    val accessToken: String,
    val user: AuthenticatedUser
)
