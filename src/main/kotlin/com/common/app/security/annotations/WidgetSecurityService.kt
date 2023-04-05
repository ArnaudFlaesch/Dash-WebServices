package com.common.app.security.annotations

import com.common.domain.service.UserService
import com.dash.domain.model.WidgetDomain
import com.dash.infra.adapter.WidgetPersistenceAdapter
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class WidgetSecurityService(
    val userService: UserService,
    val widgetPersistenceAdapter: WidgetPersistenceAdapter
) {

    fun doesWidgetBelongToUser(userDetails: UserDetails, widgetId: Int): Boolean {
        val authUser = userService.getCurrentAuthenticatedUser()
        val userWidgets = widgetPersistenceAdapter.getUserWidgets(authUser.id)
        return userWidgets.map(WidgetDomain::id).contains(widgetId)
    }
}
