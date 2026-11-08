package com.dash.dashboard.application.security

import com.dash.dashboard.application.port.outbound.*
import com.dash.dashboard.domain.model.tab.Tab
import com.dash.dashboard.domain.model.widget.Widget
import com.dash.shared.application.port.outbound.CurrentUserPort
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

/**
 * Checks, from @PreAuthorize expressions, that the resources a request targets belong to the authenticated user.
 * A resource that doesn't exist is reported as not owned, so the request is denied instead of failing later.
 */
@Component
@Transactional(readOnly = true)
class OwnershipSecurityService(
    private val currentUserPort: CurrentUserPort,
    private val tabPersistencePort: TabPersistencePort,
    private val widgetPersistencePort: WidgetPersistencePort,
    private val miniWidgetPersistencePort: MiniWidgetPersistencePort,
    private val incidentPersistencePort: IncidentPersistencePort,
    private val followedUserPersistencePort: FollowedUserPersistencePort,
    private val workoutPersistencePort: WorkoutPersistencePort
) {
    fun isTabOwner(tabId: Int): Boolean = tabPersistencePort.isTabOwnedBy(tabId, currentUserId())

    fun areTabsOwned(tabs: Collection<Tab>): Boolean =
        areAllOwned(tabs.map(Tab::id)) { ids -> tabPersistencePort.findTabIdsOwnedBy(ids, currentUserId()) }

    fun isWidgetOwner(widgetId: Int): Boolean = widgetPersistencePort.isWidgetOwnedBy(widgetId, currentUserId())

    fun areWidgetsOwned(widgets: Collection<Widget>): Boolean =
        areAllOwned(widgets.map(Widget::id)) { ids ->
            widgetPersistencePort.findWidgetIdsOwnedBy(ids, currentUserId())
        }

    fun isMiniWidgetOwner(miniWidgetId: Int): Boolean =
        miniWidgetPersistencePort.isMiniWidgetOwnedBy(miniWidgetId, currentUserId())

    fun isIncidentOwner(incidentId: Int): Boolean =
        incidentPersistencePort.isIncidentOwnedBy(incidentId, currentUserId())

    fun isFollowedUserOwner(followedUserId: Int): Boolean =
        followedUserPersistencePort.isFollowedUserOwnedBy(followedUserId, currentUserId())

    fun isWorkoutSessionOwner(workoutSessionId: Int): Boolean =
        workoutPersistencePort.isWorkoutSessionOwnedBy(workoutSessionId, currentUserId())

    fun isWorkoutTypeOwner(workoutTypeId: Int): Boolean =
        workoutPersistencePort.isWorkoutTypeOwnedBy(workoutTypeId, currentUserId())

    private fun currentUserId(): Int = currentUserPort.currentUserId()

    /** True if every id is among the ids [findOwnedIds] returns as owned by the authenticated user. */
    private fun areAllOwned(
        ids: Collection<Int>,
        findOwnedIds: (Set<Int>) -> Collection<Int>
    ): Boolean = ids.toSet().let { idSet -> findOwnedIds(idSet).toSet() == idSet }
}
