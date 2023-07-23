package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.WidgetDomain
import com.dash.infra.adapter.WidgetPersistenceAdapter
import com.dash.infra.repository.IncidentWidgetRepository
import org.springframework.stereotype.Component

@Component
class IncidentWidgetSecurityService(
    val userService: UserService,
    val widgetPersistenceAdapter: WidgetPersistenceAdapter,
    val incidentWidgetRepository: IncidentWidgetRepository
) {

    fun doesIncidentBelongToAuthenticatedUser(incidentId: Int): Boolean {
        val authUser = userService.getCurrentAuthenticatedUser()
        val incidentConfig = incidentWidgetRepository.getReferenceById(incidentId)
        val userWidgets = widgetPersistenceAdapter.getUserWidgets(authUser.id)
        return userWidgets.map(WidgetDomain::id).contains(incidentConfig.widget.id)
    }
}
