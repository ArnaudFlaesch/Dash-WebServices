package com.cashmanager.infra.adapter

import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import com.cashmanager.infra.entity.ExpenseEntity
import com.cashmanager.infra.entity.TotalExpenseByMonthEntity
import com.cashmanager.infra.repository.ExpenseRepository
import com.cashmanager.infra.repository.LabelRepository
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ExpensePersistenceAdapter(
    private val expenseRepository: ExpenseRepository,
    private val labelRepository: LabelRepository
) {
    fun getExpensesByInterval(
        startIntervalDate: LocalDate,
        endIntervalDate: LocalDate,
        authenticatedUserId: Int
    ): List<ExpenseDomain> =
        expenseRepository
            .findAllByLabelUserIdAndExpenseDateBetweenOrderByExpenseDateAsc(
                authenticatedUserId,
                startIntervalDate,
                endIntervalDate
            ).map(ExpenseEntity::toDomain)

    fun getAllUserExpenses(authenticatedUserId: Int): List<ExpenseDomain> =
        expenseRepository.findAllByLabelUserId(authenticatedUserId).map(ExpenseEntity::toDomain)

    fun getUserTotalExpensesByMonth(authenticatedUserId: Int): List<TotalExpenseByMonthDomain> =
        expenseRepository
            .getTotalExpensesByMonth(
                authenticatedUserId
            ).map(TotalExpenseByMonthEntity::toDomain)

    fun getUserTotalExpensesByMonthByLabelId(
        labelId: Int,
        authenticatedUserId: Int
    ): List<TotalExpenseByMonthDomain> =
        expenseRepository
            .getTotalExpensesByMonthByLabelId(
                labelId,
                authenticatedUserId
            ).map(TotalExpenseByMonthEntity::toDomain)

    fun insertExpense(expense: ExpenseDomain): ExpenseDomain =
        ExpenseEntity(
            id = 0,
            amount = expense.amount,
            expenseDate = expense.expenseDate,
            label = labelRepository.getReferenceById(expense.labelId)
        ).let(expenseRepository::save)
            .let(ExpenseEntity::toDomain)

    fun deleteExpense(expenseId: Int) =
        expenseRepository
            .getReferenceById(expenseId)
            .let(expenseRepository::delete)

    fun deleteExpensesByLabelId(labelId: Int) = expenseRepository.deleteExpensesByLabelId(labelId)
}
