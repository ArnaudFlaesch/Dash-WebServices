package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.widget.Widget

interface WidgetUseCase {
    fun getTabWidgets(tabId: Int): List<Widget>

    /** Adds a widget of the given type at the end of the tab. */
    fun addWidget(
        widgetType: Int,
        tabId: Int
    ): Widget

    fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): Widget

    fun updateWidgetsOrder(widgetList: List<Widget>): List<Widget>

    fun deleteWidget(widgetId: Int)
}
