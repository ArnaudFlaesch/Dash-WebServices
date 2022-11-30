package com.cashmanager.domain.service

import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import com.cashmanager.infra.adapter.ExpensePersistenceAdapter
import com.common.domain.service.UserService
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ExpenseService(
    private val expensePersistenceAdapter: ExpensePersistenceAdapter,
    private val userService: UserService
) {

    fun getExpensesByInterval(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<ExpenseDomain> =
        expensePersistenceAdapter.getExpensesByInterval(startIntervalDate, endIntervalDate)

    fun getAllExpenses(): List<ExpenseDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return expensePersistenceAdapter.getAllUserExpenses(currentAuthenticatedUserId)
    }

    fun getTotalExpensesByMonth(): List<TotalExpenseByMonthDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return expensePersistenceAdapter.getUserTotalExpensesByMonth(currentAuthenticatedUserId)
    }

    fun getTotalExpensesByMonthByLabelId(labelId: Int): List<TotalExpenseByMonthDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return expensePersistenceAdapter.getUserTotalExpensesByMonthByLabelId(labelId, currentAuthenticatedUserId)
    }

    fun addExpense(amount: Float, expenseDate: LocalDate, labelId: Int): ExpenseDomain {
        val expenseToCreate = ExpenseDomain(0, amount, expenseDate, labelId)
        return insertExpense(expenseToCreate)
    }

    fun insertExpense(expense: ExpenseDomain): ExpenseDomain = expensePersistenceAdapter.insertExpense(expense)

    fun deleteExpense(expenseId: Int) = expensePersistenceAdapter.deleteExpense(expenseId)

    fun deleteExpensesByLabelId(labelId: Int) = expensePersistenceAdapter.deleteExpensesByLabelId(labelId)
}
