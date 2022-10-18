package com.dash.domain.model.config

import com.dash.domain.model.TabDomain
import com.dash.domain.model.WidgetDomain

data class ImportData(
    val widgets: List<WidgetDomain> = listOf(),
    val tabs: List<TabDomain> = listOf()
)
