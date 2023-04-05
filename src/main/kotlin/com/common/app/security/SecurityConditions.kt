package com.common.app.security

object SecurityConditions {
    const val isUserAdmin = "hasRole('ROLE_ADMIN')"

    // Tabs
    const val doesTabsBelongToAuthenticatedUser = "filterObject != authentication.principal.id"
    const val doesTabBelongToAuthenticatedUser = "@tabSecurityService.doesTabBelongToUser(principal, #tabId)"

    // Widgets
    const val doesWidgetBelongToAuthenticatedUser = "@widgetSecurityService.doesWidgetBelongToUser(principal, #widgetId)"
}
