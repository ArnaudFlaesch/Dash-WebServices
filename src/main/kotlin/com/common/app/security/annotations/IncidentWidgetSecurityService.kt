package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.incidentWidget.IncidentDomain
import com.dash.infra.adapter.IncidentWidgetAdapter
import com.dash.infra.adapter.WidgetPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class IncidentWidgetSecurityService(
    val userService: UserService,
    val widgetPersistenceAdapter: WidgetPersistenceAdapter,
    val incidentWidgetAdapter: IncidentWidgetAdapter
) {

    fun doesIncidentBelongToAuthenticatedUser(incidentId: Int): Boolean {
        val authUser = userService.getCurrentAuthenticatedUser()
        val userWidgets = widgetPersistenceAdapter.getUserWidgets(authUser.id)
        val userIncidentWidgets = userWidgets.map { widget -> incidentWidgetAdapter.getIncidentConfigForWidget(widget.id) }
        return userIncidentWidgets.map(IncidentDomain::id).contains(incidentId)
    }
}
