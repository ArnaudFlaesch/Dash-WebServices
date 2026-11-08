package com.dash.shared.application.port.outbound

import com.dash.shared.domain.model.AuthenticatedUser

fun interface UserAuthenticationPort {
    /**
     * Checks the credentials and makes the user the current one, or throws if they are wrong.
     */
    fun authenticate(
        username: String,
        password: String
    ): AuthenticatedUser
}
