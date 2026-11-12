package com.dash.cashmanager.domain.model

data class CashManagerImportData(
    val expenses: List<ExpenseExport> = listOf(),
    val labels: List<LabelExport> = listOf()
) {
    /** Each imported label with its expenses, in the order of the labels. */
    fun labelsWithExpenses(): List<Pair<LabelExport, List<ExpenseExport>>> =
        expenses.groupBy(ExpenseExport::labelId).let { expensesByLabelId ->
            labels.map { label -> label to expensesByLabelId[label.id].orEmpty() }
        }
}
