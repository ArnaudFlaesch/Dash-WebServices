package com.dash.shared.application.security

/** SpEL expressions for @PreAuthorize, shared by every bounded context. */
object SecurityConditions {
    const val IS_USER_ADMIN = "hasRole('ROLE_ADMIN')"
}
