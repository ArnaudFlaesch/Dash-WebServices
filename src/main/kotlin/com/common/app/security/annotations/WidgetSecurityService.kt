package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.WidgetDomain
import com.dash.infra.adapter.WidgetPersistenceAdapter
import org.springframework.stereotype.Component

@Component
class WidgetSecurityService(
    val userService: UserService,
    val widgetPersistenceAdapter: WidgetPersistenceAdapter
) {
    fun doesWidgetBelongToUser(widgetId: Int): Boolean =
        userService
            .getCurrentAuthenticatedUserId()
            .let(widgetPersistenceAdapter::getUserWidgets)
            .map(WidgetDomain::id)
            .contains(widgetId)
}
