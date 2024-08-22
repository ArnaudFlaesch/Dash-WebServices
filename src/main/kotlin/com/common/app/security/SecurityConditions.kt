package com.common.app.security

object SecurityConditions {
    const val IS_USER_ADMIN = "hasRole('ROLE_ADMIN')"

    // Tabs
    const val DOES_TABS_BELONG_TO_AUTHENTICATED_USER = "filterObject != authentication.principal.id"
    const val DOES_TAB_BELONG_TO_AUTHENTICATED_USER = "@tabSecurityService.doesTabBelongToUser(#tabId)"

    // Widgets
    const val DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER =
        "@widgetSecurityService.doesWidgetBelongToUser(#widgetId)"

    // Incident widget
    const val DOES_INCIDENT_BELONG_TO_AUTHENTICATED_USER =
        "@incidentWidgetSecurityService.doesIncidentBelongToAuthenticatedUser(#incidentId)"
}
