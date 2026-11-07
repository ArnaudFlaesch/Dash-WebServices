package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.MiniWidgetUseCase
import com.dash.dashboard.application.port.outbound.MiniWidgetPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.widget.MiniWidget
import com.dash.shared.application.port.outbound.CurrentUserPort
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
class MiniWidgetService(
    private val miniWidgetPersistencePort: MiniWidgetPersistencePort,
    private val currentUserPort: CurrentUserPort
) : MiniWidgetUseCase {
    override fun getUserMiniWidgets(): List<MiniWidget> =
        miniWidgetPersistencePort.getUserMiniWidgets(currentUserPort.currentUserId())

    override fun addMiniWidget(widgetType: Int): MiniWidget =
        MiniWidget(
            id = 0,
            type = widgetType,
            data = null,
            userId = currentUserPort.currentUserId()
        ).let(miniWidgetPersistencePort::saveMiniWidget)

    @PreAuthorize(DashboardSecurityConditions.DOES_MINI_WIDGET_BELONG_TO_AUTHENTICATED_USER)
    override fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): MiniWidget = miniWidgetPersistencePort.updateWidgetData(widgetId, updatedData)

    @PreAuthorize(
        "${DashboardSecurityConditions.DOES_MINI_WIDGET_BELONG_TO_AUTHENTICATED_USER} and " +
            DashboardSecurityConditions.IS_USER_ADMIN
    )
    override fun deleteMiniWidget(widgetId: Int) {
        miniWidgetPersistencePort.deleteMiniWidget(widgetId)
    }
}
