package com.dash.dashboard.domain.model.config

import com.dash.dashboard.domain.model.widget.Widget

data class ImportWidget(
    val type: Int,
    val data: Any?,
    val widgetOrder: Int,
    val tabId: Int
) {
    /** The widget to import, in the tab its exported tab was imported as. */
    fun toNewWidget(importedTabId: Int): Widget =
        Widget(id = 0, type = type, data = data, widgetOrder = widgetOrder, tabId = importedTabId)
}
