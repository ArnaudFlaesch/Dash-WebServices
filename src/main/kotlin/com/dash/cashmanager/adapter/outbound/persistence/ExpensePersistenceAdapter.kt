package com.dash.cashmanager.adapter.outbound.persistence

import com.dash.cashmanager.application.port.outbound.ExpensePersistencePort
import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class ExpensePersistenceAdapter(
    private val expenseRepository: ExpenseRepository,
    private val labelRepository: LabelRepository
) : ExpensePersistencePort {
    override fun getExpensesByInterval(
        startIntervalDate: LocalDate,
        endIntervalDate: LocalDate,
        userId: Int
    ): List<Expense> =
        expenseRepository
            .findAllByLabelUserIdAndExpenseDateBetweenOrderByExpenseDateAsc(
                userId,
                startIntervalDate,
                endIntervalDate
            ).map(ExpenseEntity::toDomain)

    override fun getAllUserExpenses(userId: Int): List<Expense> =
        expenseRepository.findAllByLabelUserId(userId).map(ExpenseEntity::toDomain)

    override fun getUserTotalExpensesByMonth(userId: Int): List<TotalExpenseByMonth> =
        expenseRepository
            .getTotalExpensesByMonth(
                userId
            ).map(TotalExpenseByMonthEntity::toDomain)

    override fun getUserTotalExpensesByMonthByLabelId(
        labelId: Int,
        userId: Int
    ): List<TotalExpenseByMonth> =
        expenseRepository
            .getTotalExpensesByMonthByLabelId(
                labelId,
                userId
            ).map(TotalExpenseByMonthEntity::toDomain)

    override fun insertExpense(expense: Expense): Expense =
        ExpenseEntity(
            id = 0,
            amount = expense.amount,
            expenseDate = expense.expenseDate,
            label = labelRepository.getReferenceById(expense.labelId)
        ).let(expenseRepository::save)
            .let(ExpenseEntity::toDomain)

    @Transactional
    override fun updateExpense(expense: Expense): Expense =
        expenseRepository
            .getReferenceById(expense.id)
            .apply {
                amount = expense.amount
                expenseDate = expense.expenseDate
                label = labelRepository.getReferenceById(expense.labelId)
            }.toDomain()

    override fun deleteExpense(expenseId: Int) {
        expenseRepository.deleteById(expenseId)
    }

    override fun deleteExpensesByLabelId(labelId: Int) {
        expenseRepository.deleteExpensesByLabelId(labelId)
    }
}
