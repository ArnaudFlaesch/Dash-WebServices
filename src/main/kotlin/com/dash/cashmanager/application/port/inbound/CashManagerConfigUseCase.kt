package com.dash.cashmanager.application.port.inbound

import com.dash.cashmanager.domain.model.CashManagerExportData
import com.dash.cashmanager.domain.model.CashManagerImportData
import com.dash.cashmanager.domain.model.Expense

interface CashManagerConfigUseCase {
    fun exportData(): CashManagerExportData

    /** Imports the labels and their expenses for the authenticated user, returning the imported expenses. */
    fun importData(importData: CashManagerImportData): List<Expense>
}
