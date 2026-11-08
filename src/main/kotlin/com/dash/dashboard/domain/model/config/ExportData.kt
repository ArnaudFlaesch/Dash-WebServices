package com.dash.dashboard.domain.model.config

import com.dash.dashboard.domain.model.tab.Tab
import com.dash.dashboard.domain.model.widget.Widget

data class ExportData(
    val widgets: List<Widget>,
    val tabs: List<Tab>
)
