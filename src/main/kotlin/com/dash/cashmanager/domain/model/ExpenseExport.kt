package com.dash.cashmanager.domain.model

import java.time.LocalDate

data class ExpenseExport(
    val id: Int,
    val amount: Float,
    val expenseDate: String,
    val labelId: Int
) {
    /** The expense to import, attached to the label its exported label was imported as. */
    fun toNewExpense(importedLabelId: Int): Expense = Expense.new(amount, LocalDate.parse(expenseDate), importedLabelId)
}
