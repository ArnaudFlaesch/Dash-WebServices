package com.cashmanager.domain.model

data class ImportData(
    val expenses: List<ExpenseExportDomain> = listOf(),
    val labels: List<LabelDomain> = listOf()
)
