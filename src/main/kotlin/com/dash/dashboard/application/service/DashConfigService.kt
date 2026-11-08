package com.dash.dashboard.application.service

import com.dash.dashboard.application.port.inbound.DashConfigUseCase
import com.dash.dashboard.application.port.outbound.TabPersistencePort
import com.dash.dashboard.application.port.outbound.WidgetPersistencePort
import com.dash.dashboard.application.security.DashboardSecurityConditions
import com.dash.dashboard.domain.model.config.ExportData
import com.dash.dashboard.domain.model.config.ImportData
import com.dash.dashboard.domain.model.config.ImportWidget
import com.dash.dashboard.domain.model.widget.Widget
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.application.port.outbound.EventPublisherPort
import com.dash.shared.domain.event.DashEvent
import com.dash.shared.domain.event.EventMessages
import com.dash.shared.domain.model.NotificationType
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@PreAuthorize(DashboardSecurityConditions.IS_USER_ADMIN)
class DashConfigService(
    private val tabPersistencePort: TabPersistencePort,
    private val widgetPersistencePort: WidgetPersistencePort,
    private val currentUserPort: CurrentUserPort,
    private val eventPublisherPort: EventPublisherPort
) : DashConfigUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun exportConfig(): ExportData =
        currentUserPort
            .currentUserId()
            .let { userId ->
                ExportData(
                    widgets = widgetPersistencePort.getUserWidgets(userId),
                    tabs = tabPersistencePort.getUserTabs(userId)
                )
            }.also { eventPublisherPort.publish(DashEvent(EventMessages.CONFIG_EXPORTED, NotificationType.WARN)) }

    /** Imports the tabs and their widgets in a single transaction, so a failing import leaves no partial data. */
    @Transactional
    override fun importConfig(importData: ImportData): List<Widget> =
        importData.widgets
            .groupBy(ImportWidget::tabId)
            .let { widgetsByTabId ->
                val userId = currentUserPort.currentUserId()
                importData.tabs.flatMap { tab ->
                    val insertedTab = tabPersistencePort.importTab(tab.label, tab.tabOrder, userId)
                    widgetsByTabId[tab.id].orEmpty().map { widget ->
                        widgetPersistencePort.saveWidget(
                            Widget(
                                id = 0,
                                type = widget.type,
                                data = widget.data,
                                widgetOrder = widget.widgetOrder,
                                tabId = insertedTab.id
                            )
                        )
                    }
                }
            }.also { logger.info("Import terminé : {} widgets importés", it.size) }
}
