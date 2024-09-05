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
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class ExpenseService(
    private val expensePersistenceAdapter: ExpensePersistenceAdapter,
    private val userService: UserService
) {
    fun getExpensesByInterval(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<ExpenseDomain> =
        expensePersistenceAdapter.getExpensesByInterval(
            startIntervalDate,
            endIntervalDate,
            userService.getCurrentAuthenticatedUserId()
        )

    fun getUserExpenses(): List<ExpenseDomain> =
        expensePersistenceAdapter.getAllUserExpenses(userService.getCurrentAuthenticatedUserId())

    fun getTotalExpensesByMonth(): List<TotalExpenseByMonthDomain> =
        expensePersistenceAdapter.getUserTotalExpensesByMonth(userService.getCurrentAuthenticatedUserId())

    fun getTotalExpensesByMonthByLabelId(labelId: Int): List<TotalExpenseByMonthDomain> =
        expensePersistenceAdapter.getUserTotalExpensesByMonthByLabelId(
            labelId,
            userService.getCurrentAuthenticatedUserId()
        )

    fun addExpense(amount: Float, expenseDate: LocalDate, labelId: Int): ExpenseDomain =
        ExpenseDomain(0, amount, expenseDate, labelId)
            .let(this::insertExpense)

    fun insertExpense(expense: ExpenseDomain): ExpenseDomain = expensePersistenceAdapter.insertExpense(expense)

    fun deleteExpense(expenseId: Int) = expensePersistenceAdapter.deleteExpense(expenseId)

    fun deleteExpensesByLabelId(labelId: Int) = expensePersistenceAdapter.deleteExpensesByLabelId(labelId)
}
