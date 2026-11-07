package com.dash.cashmanager.application.service

import com.dash.cashmanager.application.port.inbound.ExpenseUseCase
import com.dash.cashmanager.application.port.outbound.ExpensePersistencePort
import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.application.security.SecurityConditions
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class ExpenseService(
    private val expensePersistencePort: ExpensePersistencePort,
    private val currentUserPort: CurrentUserPort
) : ExpenseUseCase {
    override fun getExpensesByInterval(
        startIntervalDate: LocalDate,
        endIntervalDate: LocalDate
    ): List<Expense> =
        expensePersistencePort.getExpensesByInterval(
            startIntervalDate,
            endIntervalDate,
            currentUserPort.currentUserId()
        )

    override fun getTotalExpensesByMonth(): List<TotalExpenseByMonth> =
        expensePersistencePort.getUserTotalExpensesByMonth(currentUserPort.currentUserId())

    override fun getTotalExpensesByMonthByLabelId(labelId: Int): List<TotalExpenseByMonth> =
        expensePersistencePort.getUserTotalExpensesByMonthByLabelId(labelId, currentUserPort.currentUserId())

    override fun addExpense(
        amount: Float,
        expenseDate: LocalDate,
        labelId: Int
    ): Expense = expensePersistencePort.insertExpense(Expense(0, amount, expenseDate, labelId))

    override fun updateExpense(expense: Expense): Expense = expensePersistencePort.updateExpense(expense)

    override fun deleteExpense(expenseId: Int) {
        expensePersistencePort.deleteExpense(expenseId)
    }
}
