package com.dash.dashboard.domain.model.widget

data class Widget(
    val id: Int,
    val type: Int,
    val data: Any?,
    val widgetOrder: Int,
    val tabId: Int
) {
    companion object {
        /** A widget not saved yet, without data, placed after the tab's [numberOfWidgets] existing widgets. */
        fun newAfter(
            numberOfWidgets: Int,
            type: Int,
            tabId: Int
        ): Widget = Widget(id = 0, type = type, data = null, widgetOrder = numberOfWidgets + 1, tabId = tabId)
    }
}
