package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.WidgetDomain
import com.dash.infra.adapter.WidgetPersistenceAdapter
import com.dash.infra.repository.IncidentWidgetRepository
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class IncidentWidgetSecurityService(
    val userService: UserService,
    val widgetPersistenceAdapter: WidgetPersistenceAdapter,
    val incidentWidgetRepository: IncidentWidgetRepository
) {
    @Transactional(readOnly = true)
    fun doesIncidentBelongToAuthenticatedUser(incidentId: Int): Boolean {
        val incidentWidgetId = incidentWidgetRepository.getReferenceById(incidentId).widget.id
        return userService
            .getCurrentAuthenticatedUserId()
            .let(widgetPersistenceAdapter::getUserWidgets)
            .map(WidgetDomain::id)
            .contains(incidentWidgetId)
    }
}
