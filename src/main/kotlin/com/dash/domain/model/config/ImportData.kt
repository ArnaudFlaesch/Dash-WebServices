package com.dash.domain.model.config

data class ImportData(
    val widgets: List<WidgetConfigData> = listOf(),
    val tabs: List<TabConfigData> = listOf()
)
