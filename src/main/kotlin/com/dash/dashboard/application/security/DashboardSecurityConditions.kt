package com.dash.dashboard.application.security

import com.dash.shared.application.security.SecurityConditions

/** SpEL expressions for @PreAuthorize/@PostFilter. Ownership checks are implemented in OwnershipSecurityService. */
object DashboardSecurityConditions {
    private const val OWNERSHIP = "@ownershipSecurityService"

    const val IS_USER_ADMIN = SecurityConditions.IS_USER_ADMIN

    // Tabs
    const val DOES_FILTERED_TAB_BELONG_TO_AUTHENTICATED_USER = "filterObject.userId == authentication.principal.id"
    const val DOES_TAB_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.isTabOwner(#tabId)"
    const val DO_TABS_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.areTabsOwned(#tabList)"

    // Widgets
    const val DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.isWidgetOwner(#widgetId)"
    const val DO_WIDGETS_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.areWidgetsOwned(#widgetList)"
    const val DOES_MINI_WIDGET_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.isMiniWidgetOwner(#widgetId)"

    // Incident widget
    const val DOES_INCIDENT_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.isIncidentOwner(#incidentId)"

    // Twitter widget
    const val DOES_FOLLOWED_USER_BELONG_TO_AUTHENTICATED_USER = "$OWNERSHIP.isFollowedUserOwner(#followedUserId)"

    // Workout widget
    const val DOES_WORKOUT_SESSION_BELONG_TO_AUTHENTICATED_USER =
        "$OWNERSHIP.isWorkoutSessionOwner(#workoutSessionId)"
    const val DOES_WORKOUT_EXERCISE_BELONG_TO_AUTHENTICATED_USER =
        "$DOES_WORKOUT_SESSION_BELONG_TO_AUTHENTICATED_USER and $OWNERSHIP.isWorkoutTypeOwner(#workoutTypeId)"
}
