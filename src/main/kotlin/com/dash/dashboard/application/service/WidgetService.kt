package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.WidgetUseCase
import com.dash.dashboard.application.port.outbound.WidgetPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.widget.Widget
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class WidgetService(
    private val widgetPersistencePort: WidgetPersistencePort
) : WidgetUseCase {
    @PreAuthorize(DashboardSecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER)
    override fun getTabWidgets(tabId: Int): List<Widget> = widgetPersistencePort.getTabWidgets(tabId)

    @PreAuthorize(DashboardSecurityConditions.DOES_TAB_BELONG_TO_AUTHENTICATED_USER)
    override fun addWidget(
        widgetType: Int,
        tabId: Int
    ): Widget =
        Widget(
            id = 0,
            type = widgetType,
            data = null,
            widgetOrder = widgetPersistencePort.getNumberOfWidgetsByTab(tabId) + 1,
            tabId = tabId
        ).let(widgetPersistencePort::saveWidget)

    @PreAuthorize(DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): Widget = widgetPersistencePort.updateWidgetData(widgetId, updatedData)

    @PreAuthorize(DashboardSecurityConditions.DO_WIDGETS_BELONG_TO_AUTHENTICATED_USER)
    override fun updateWidgetsOrder(widgetList: List<Widget>): List<Widget> =
        widgetPersistencePort.updateWidgetsOrder(widgetList)

    @PreAuthorize(
        "${DashboardSecurityConditions.DOES_WIDGET_BELONG_TO_AUTHENTICATED_USER} and " +
            DashboardSecurityConditions.IS_USER_ADMIN
    )
    override fun deleteWidget(widgetId: Int) {
        widgetPersistencePort.deleteWidget(widgetId)
    }
}
