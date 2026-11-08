package com.dash.cashmanager.domain.model

data class CashManagerExportData(
    val expenses: List<ExpenseExport>,
    val labels: List<Label>
)
