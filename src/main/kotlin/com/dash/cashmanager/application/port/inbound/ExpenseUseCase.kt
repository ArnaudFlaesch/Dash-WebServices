package com.dash.cashmanager.application.port.inbound

import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import java.time.LocalDate

interface ExpenseUseCase {
    fun getExpensesByInterval(
        startIntervalDate: LocalDate,
        endIntervalDate: LocalDate
    ): List<Expense>

    fun getTotalExpensesByMonth(): List<TotalExpenseByMonth>

    fun getTotalExpensesByMonthByLabelId(labelId: Int): List<TotalExpenseByMonth>

    fun addExpense(
        amount: Float,
        expenseDate: LocalDate,
        labelId: Int
    ): Expense

    fun updateExpense(expense: Expense): Expense

    fun deleteExpense(expenseId: Int)
}
