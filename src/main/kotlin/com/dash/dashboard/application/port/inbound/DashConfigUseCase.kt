package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.config.ExportData
import com.dash.dashboard.domain.model.config.ImportData
import com.dash.dashboard.domain.model.widget.Widget

interface DashConfigUseCase {
    fun exportConfig(): ExportData

    /** Imports the tabs and their widgets for the authenticated user, returning the imported widgets. */
    fun importConfig(importData: ImportData): List<Widget>
}
