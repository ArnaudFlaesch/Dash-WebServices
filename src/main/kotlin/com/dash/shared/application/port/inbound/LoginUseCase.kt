package com.dash.shared.application.port.inbound

import com.dash.shared.domain.model.UserSession

fun interface LoginUseCase {
    /** Checks the credentials and opens a session, or fails if they are wrong. */
    fun login(
        username: String,
        password: String
    ): UserSession
}
