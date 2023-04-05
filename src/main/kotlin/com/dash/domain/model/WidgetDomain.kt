package com.dash.domain.model

data class WidgetDomain(
    val id: Int,
    val type: Int,
    val data: Any?,
    val widgetOrder: Int,
    val tabId: Int
)
