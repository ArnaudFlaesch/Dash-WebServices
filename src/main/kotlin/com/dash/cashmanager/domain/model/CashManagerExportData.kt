package com.dash.cashmanager.domain.model

data class CashManagerExportData(
    val expenses: List<ExpenseExport>,
    val labels: List<Label>
) {
    companion object {
        fun of(
            expenses: List<Expense>,
            labels: List<Label>
        ): CashManagerExportData = CashManagerExportData(expenses.map(Expense::toExport), labels)
    }
}
