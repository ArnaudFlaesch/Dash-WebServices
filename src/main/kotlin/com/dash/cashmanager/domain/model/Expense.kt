package com.dash.cashmanager.domain.model

import java.time.LocalDate

data class Expense(
    val id: Int,
    val amount: Float,
    val expenseDate: LocalDate,
    val labelId: Int
) {
    fun toExport(): ExpenseExport = ExpenseExport(id, amount, expenseDate.toString(), labelId)

    companion object {
        /** An expense not saved yet. */
        fun new(
            amount: Float,
            expenseDate: LocalDate,
            labelId: Int
        ): Expense = Expense(0, amount, expenseDate, labelId)
    }
}
