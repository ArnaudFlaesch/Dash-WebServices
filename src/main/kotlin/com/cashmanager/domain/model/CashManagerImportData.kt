package com.cashmanager.domain.model

data class CashManagerImportData(
    val expenses: List<ExpenseExportDomain> = listOf(),
    val labels: List<LabelExportDomain> = listOf()
)
