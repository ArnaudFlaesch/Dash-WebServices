package com.dash.domain.service

import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import com.dash.domain.model.MiniWidgetDomain
import com.dash.infra.adapter.MiniWidgetPersistenceAdapter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class MiniWidgetService(
    private val miniWidgetPersistenceAdapter: MiniWidgetPersistenceAdapter,
    private val userService: UserService
) {
    fun findAuthenticatedUserMiniWidgets(): List<MiniWidgetDomain> =
        userService
            .getCurrentAuthenticatedUserId()
            .let(miniWidgetPersistenceAdapter::findAuthenticatedUserMiniWidgets)

    fun addMiniWidget(widgetType: Int): MiniWidgetDomain =
        userService
            .getCurrentAuthenticatedUserId()
            .let { currentAuthenticatedUserId ->
                MiniWidgetDomain(
                    id = 0,
                    type = widgetType,
                    data = null,
                    userId = currentAuthenticatedUserId
                )
            }.let(this::saveMiniWidget)

    fun saveMiniWidget(miniWidget: MiniWidgetDomain): MiniWidgetDomain =
        miniWidgetPersistenceAdapter.saveMiniWidget(miniWidget)

    fun updateWidgetData(widgetId: Int, updatedData: Any): MiniWidgetDomain =
        miniWidgetPersistenceAdapter.updateWidgetData(widgetId, updatedData)

    @PreAuthorize(SecurityConditions.IS_USER_ADMIN)
    fun deleteMiniWidget(widgetId: Int) = miniWidgetPersistenceAdapter.deleteMiniWidget(widgetId)
}
