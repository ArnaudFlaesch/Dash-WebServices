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

    @PreAuthorize(SecurityConditions.doesTabBelongToAuthenticatedUser)
    fun findByTabIdOrderByWidgetOrderAsc(
        tabId: Int
    ): List<WidgetDomain> = widgetPersistenceAdapter.findByTabIdOrderByWidgetOrderAsc(tabId)

    fun getUserWidgets(): List<WidgetDomain> {
        val userId = userService.getCurrentAuthenticatedUser().id
        return widgetPersistenceAdapter.getUserWidgets(userId)
    }

    fun addWidget(widgetType: Int, tabId: Int): WidgetDomain {
        val widgetOrder = widgetPersistenceAdapter.getNumberOfWidgetsByTab(tabId) + 1
        return saveWidget(WidgetDomain(id = 0, type = widgetType, widgetOrder = widgetOrder, tabId = tabId, data = null))
    }

    fun saveWidget(widget: WidgetDomain): WidgetDomain = widgetPersistenceAdapter.saveWidget(widget)

    fun importWidget(widgetType: Int, widgetOrder: Int, data: Any?, tabId: Int): WidgetDomain {
        val widget = WidgetDomain(id = 0, type = widgetType, widgetOrder = widgetOrder, data = data, tabId = tabId)
        return widgetPersistenceAdapter.saveWidget(widget)
    }

    fun updateWidgetData(widgetId: Int, updatedData: Any): WidgetDomain = widgetPersistenceAdapter.updateWidgetData(widgetId, updatedData)

    fun updateWidgetsOrder(widgetList: List<WidgetDomain>): List<WidgetDomain> = widgetPersistenceAdapter.updateWidgetsOrder(widgetList)

    @PreAuthorize("${SecurityConditions.doesWidgetBelongToAuthenticatedUser} and ${SecurityConditions.isUserAdmin}")
    fun deleteWidget(widgetId: Int) = widgetPersistenceAdapter.deleteWidget(widgetId)
}
