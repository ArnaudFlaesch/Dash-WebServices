package com.dash.dashboard.domain.model.config

data class ImportWidget(
    val type: Int,
    val data: Any?,
    val widgetOrder: Int,
    val tabId: Int
)
