package com.cashmanager.domain.model

data class CashManagerExportData(
    val expenses: List<ExpenseExportDomain>,
    val labels: List<LabelDomain>
)
