package com.dash.dashboard.domain.model.config

data class ImportData(
    val widgets: List<ImportWidget> = listOf(),
    val tabs: List<ImportTab> = listOf()
) {
    /** Each imported tab with its widgets, in the order of the tabs. */
    fun tabsWithWidgets(): List<Pair<ImportTab, List<ImportWidget>>> =
        widgets.groupBy(ImportWidget::tabId).let { widgetsByTabId ->
            tabs.map { tab -> tab to widgetsByTabId[tab.id].orEmpty() }
        }
}
