package com.dash.domain.model.config

import com.dash.domain.model.TabDomain
import com.dash.domain.model.WidgetDomain

data class ExportData(
    val widgets: List<WidgetDomain>,
    val tabs: List<TabDomain>
)
