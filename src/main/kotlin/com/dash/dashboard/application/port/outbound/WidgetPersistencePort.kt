package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.widget.Widget

interface WidgetPersistencePort {
    fun getTabWidgets(tabId: Int): List<Widget>

    fun getUserWidgets(userId: Int): List<Widget>

    fun getNumberOfWidgetsByTab(tabId: Int): Int

    fun saveWidget(widget: Widget): Widget

    fun updateWidgetData(
        widgetId: Int,
        updatedData: Any
    ): Widget

    fun updateWidgetsOrder(widgetList: List<Widget>): List<Widget>

    /** Deletes the widget and its data. */
    fun deleteWidget(widgetId: Int)

    fun isWidgetOwnedBy(
        widgetId: Int,
        userId: Int
    ): Boolean

    fun findWidgetIdsOwnedBy(
        widgetIds: Collection<Int>,
        userId: Int
    ): Collection<Int>
}
