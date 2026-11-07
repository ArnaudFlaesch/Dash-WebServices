package com.dash.domain.service

import com.dash.domain.model.config.ExportData
import com.dash.domain.model.config.ImportData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DashConfigService(
    private val tabService: TabService,
    private val widgetService: WidgetService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun exportConfig(): ExportData =
        ExportData(widgets = widgetService.getUserWidgets(), tabs = tabService.getUserTabs())

    /** Imports the tabs and their widgets in a single transaction, so a failing import leaves no partial data. */
    @Transactional
    fun importConfig(importData: ImportData) {
        logger.info("Import commencé")
        val widgetsByTabId = importData.widgets.groupBy { it.tabId }
        importData.tabs.forEach { tab ->
            val insertedTab = tabService.importTab(tab.label, tab.tabOrder)
            widgetsByTabId[tab.id].orEmpty().forEach { widget ->
                widgetService.importWidget(widget.type, widget.widgetOrder, widget.data, insertedTab.id)
            }
        }
        logger.info("Import terminé")
    }
}
