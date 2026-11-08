package com.dash.cashmanager.domain.model

data class CashManagerImportData(
    val expenses: List<ExpenseExport> = listOf(),
    val labels: List<LabelExport> = listOf()
)
