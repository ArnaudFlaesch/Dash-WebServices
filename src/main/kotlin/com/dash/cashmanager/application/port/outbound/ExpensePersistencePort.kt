package com.dash.cashmanager.application.port.outbound

import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import java.time.LocalDate

interface ExpensePersistencePort {
    fun getExpensesByInterval(
        startIntervalDate: LocalDate,
        endIntervalDate: LocalDate,
        userId: Int
    ): List<Expense>

    fun getAllUserExpenses(userId: Int): List<Expense>

    fun getUserTotalExpensesByMonth(userId: Int): List<TotalExpenseByMonth>

    fun getUserTotalExpensesByMonthByLabelId(
        labelId: Int,
        userId: Int
    ): List<TotalExpenseByMonth>

    fun insertExpense(expense: Expense): Expense

    fun updateExpense(expense: Expense): Expense

    fun deleteExpense(expenseId: Int)

    fun deleteExpensesByLabelId(labelId: Int)
}
