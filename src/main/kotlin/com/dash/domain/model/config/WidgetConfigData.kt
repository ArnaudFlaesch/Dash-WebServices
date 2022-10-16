package com.dash.domain.model.config

data class WidgetConfigData(
    val type: Int,
    val data: Any? = null,
    val widgetOrder: Int,
    val tabId: Int
)
