package com.dash.dashboard.domain.model.widget

data class Widget(
    val id: Int,
    val type: Int,
    val data: Any?,
    val widgetOrder: Int,
    val tabId: Int
)
