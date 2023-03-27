package com.cashmanager.domain.service

import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import com.cashmanager.infra.adapter.ExpensePersistenceAdapter
import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@PreAuthorize(SecurityConditions.isUserAdmin)
class ExpenseService(
    private val expensePersistenceAdapter: ExpensePersistenceAdapter,
    private val userService: UserService
) {

    fun getExpensesByInterval(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<ExpenseDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUser().id
        return expensePersistenceAdapter.getExpensesByInterval(startIntervalDate, endIntervalDate, currentAuthenticatedUserId)
    }

    fun getUserExpenses(): List<ExpenseDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUser().id
        return expensePersistenceAdapter.getAllUserExpenses(currentAuthenticatedUserId)
    }

    fun getTotalExpensesByMonth(): List<TotalExpenseByMonthDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUser().id
        return expensePersistenceAdapter.getUserTotalExpensesByMonth(currentAuthenticatedUserId)
    }

    fun getTotalExpensesByMonthByLabelId(labelId: Int): List<TotalExpenseByMonthDomain> {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUser().id
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
