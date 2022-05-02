package com.cashmanager.service

import com.cashmanager.controller.requests.InsertExpensePayload
import com.cashmanager.entity.Expense
import com.cashmanager.entity.Label
import com.cashmanager.model.TotalExpenseByMonth
import com.cashmanager.repository.ExpenseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ExpenseService {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    fun getExpensesByInterval(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<Expense> =
        expenseRepository.findAllByExpenseDateBetween(startIntervalDate, endIntervalDate)

    fun getAllExpenses(): List<Expense> = expenseRepository.findAll()

    fun getTotalExpensesByMonth(): List<TotalExpenseByMonth> = expenseRepository.getTotalExpensesByMonth()

    fun addExpense(expensePayload: InsertExpensePayload): Expense {
        val expenseToCreate = Expense(0, expensePayload.amount, expensePayload.expenseDate, Label(expensePayload.labelId))
        return insertExpense(expenseToCreate)
    }

    fun insertExpense(expense: Expense): Expense = expenseRepository.save(expense)

    fun deleteExpense(expenseId: Int) {
        val expense = expenseRepository.getById(expenseId)
        return expenseRepository.delete(expense)
    }

    fun deleteExpensesByLabelId(labelId: Int) = expenseRepository.deleteExpensesByLabelId(labelId)
}
