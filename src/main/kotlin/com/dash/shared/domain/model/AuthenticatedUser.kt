package com.dash.shared.domain.model

/** A user whose credentials have been checked. */
data class AuthenticatedUser(
    val id: Int,
    val username: String,
    val email: String,
    val roles: List<String>
)
