package com.common.app.security

object SecurityConditions {
    const val isUserAdmin = "hasRole('ROLE_ADMIN')"

    // Widgets
    const val doesTabBelongToAuthenticatedUser = "filterObject != authentication.principal.id"
}
