package com.cashmanager.infra.adapter

import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import com.cashmanager.infra.entity.ExpenseEntity
import com.cashmanager.infra.entity.TotalExpenseByMonthEntity
import com.cashmanager.infra.repository.ExpenseRepository
import com.cashmanager.infra.repository.LabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class ExpensePersistenceAdapter {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    @Autowired
    private lateinit var labelRepository: LabelRepository

    fun getExpensesByInterval(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<ExpenseDomain> =
        expenseRepository.findAllByExpenseDateBetweenOrderByExpenseDateAsc(startIntervalDate, endIntervalDate).map(ExpenseEntity::toDomain)

    fun getAllUserExpenses(authenticatedUserId: Int): List<ExpenseDomain> =
        expenseRepository.findAllByLabelUserId(authenticatedUserId).map(ExpenseEntity::toDomain)

    fun getUserTotalExpensesByMonth(authenticatedUserId: Int): List<TotalExpenseByMonthDomain> =
        expenseRepository.getTotalExpensesByMonth(authenticatedUserId).map(TotalExpenseByMonthEntity::toDomain)

    fun getUserTotalExpensesByMonthByLabelId(labelId: Int, authenticatedUserId: Int): List<TotalExpenseByMonthDomain> =
        expenseRepository.getTotalExpensesByMonthByLabelId(labelId, authenticatedUserId).map(TotalExpenseByMonthEntity::toDomain)

    fun insertExpense(expense: ExpenseDomain): ExpenseDomain {
        val labelEntity = labelRepository.getReferenceById(expense.labelId)
        val expenseToCreate = ExpenseEntity(id = 0, amount = expense.amount, expenseDate = expense.expenseDate, label = labelEntity)
        return expenseRepository.save(expenseToCreate).toDomain()
    }

    fun deleteExpense(expenseId: Int) {
        val expense = expenseRepository.getReferenceById(expenseId)
        return expenseRepository.delete(expense)
    }

    fun deleteExpensesByLabelId(labelId: Int) = expenseRepository.deleteExpensesByLabelId(labelId)
}
