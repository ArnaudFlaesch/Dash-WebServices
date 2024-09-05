package com.dash.domain.service

import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import com.dash.domain.model.WidgetDomain
import com.dash.infra.adapter.WidgetPersistenceAdapter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class WidgetService(
    private val userService: UserService,
    private val widgetPersistenceAdapter: WidgetPersistenceAdapter
) {
    @PreAuthorize(SecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER)
    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetDomain> =
        widgetPersistenceAdapter.findByTabIdOrderByWidgetOrderAsc(tabId)

    fun getUserWidgets(): List<WidgetDomain> {
        val userId = userService.getCurrentAuthenticatedUserId()
        return widgetPersistenceAdapter.getUserWidgets(userId)
    }

    fun addWidget(widgetType: Int, tabId: Int): WidgetDomain =
        WidgetDomain(
            id = 0,
            type = widgetType,
            widgetOrder = widgetPersistenceAdapter.getNumberOfWidgetsByTab(tabId) + 1,
            tabId = tabId,
            data = null
        ).let(this::saveWidget)

    fun saveWidget(widget: WidgetDomain): WidgetDomain = widgetPersistenceAdapter.saveWidget(widget)

    fun importWidget(widgetType: Int, widgetOrder: Int, data: Any?, tabId: Int): WidgetDomain =
        WidgetDomain(
            id = 0,
            type = widgetType,
            widgetOrder = widgetOrder,
            data = data,
            tabId = tabId
        ).let(widgetPersistenceAdapter::saveWidget)

    fun updateWidgetData(widgetId: Int, updatedData: Any): WidgetDomain =
        widgetPersistenceAdapter.updateWidgetData(widgetId, updatedData)

    fun updateWidgetsOrder(widgetList: List<WidgetDomain>): List<WidgetDomain> =
        widgetPersistenceAdapter.updateWidgetsOrder(widgetList)

    @PreAuthorize(
        "${SecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER} and ${SecurityConditions.IS_USER_ADMIN}"
    )
    fun deleteWidget(widgetId: Int) = widgetPersistenceAdapter.deleteWidget(widgetId)
}
