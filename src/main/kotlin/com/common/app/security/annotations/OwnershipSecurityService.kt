package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.TabDomain
import com.dash.domain.model.WidgetDomain
import com.dash.infra.repository.*
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Checks, from @PreAuthorize expressions, that the resources a request targets belong to the authenticated user.
 * A resource that doesn't exist is reported as not owned, so the request is denied instead of failing later.
 */
@Component
@Transactional(readOnly = true)
class OwnershipSecurityService(
    private val userService: UserService,
    private val tabRepository: TabRepository,
    private val widgetRepository: WidgetRepository,
    private val miniWidgetRepository: MiniWidgetRepository,
    private val incidentWidgetRepository: IncidentWidgetRepository,
    private val twitterWidgetRepository: TwitterWidgetRepository,
    private val workoutSessionRepository: WorkoutSessionRepository,
    private val workoutTypeRepository: WorkoutTypeRepository
) {
    fun isTabOwner(tabId: Int): Boolean = tabRepository.existsByIdAndUserId(tabId, currentUserId())

    fun areTabsOwned(tabs: Collection<TabDomain>): Boolean {
        val tabIds = tabs.map(TabDomain::id).toSet()
        return tabRepository.findIdsByIdInAndUserId(tabIds, currentUserId()).toSet() == tabIds
    }

    fun isWidgetOwner(widgetId: Int): Boolean = widgetRepository.existsByIdAndTabUserId(widgetId, currentUserId())

    fun areWidgetsOwned(widgets: Collection<WidgetDomain>): Boolean {
        val widgetIds = widgets.map(WidgetDomain::id).toSet()
        return widgetRepository.findIdsByIdInAndTabUserId(widgetIds, currentUserId()).toSet() == widgetIds
    }

    fun isMiniWidgetOwner(miniWidgetId: Int): Boolean =
        miniWidgetRepository.existsByIdAndUserId(miniWidgetId, currentUserId())

    fun isIncidentOwner(incidentId: Int): Boolean =
        incidentWidgetRepository.existsByIdAndWidgetTabUserId(incidentId, currentUserId())

    fun isFollowedUserOwner(followedUserId: Int): Boolean =
        twitterWidgetRepository.existsByIdAndUserId(followedUserId, currentUserId())

    fun isWorkoutSessionOwner(workoutSessionId: Int): Boolean =
        workoutSessionRepository.existsByIdAndUserId(workoutSessionId, currentUserId())

    fun isWorkoutTypeOwner(workoutTypeId: Int): Boolean =
        workoutTypeRepository.existsByIdAndUserId(workoutTypeId, currentUserId())

    private fun currentUserId(): Int = userService.getCurrentAuthenticatedUserId()
}
