package com.cashmanager.service

import com.cashmanager.entity.Expense
import com.cashmanager.repository.ExpenseRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ExpenseService {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    fun getExpensesByInterval(startIntervalDate: Date, endIntervalDate: Date): List<Expense> =
        expenseRepository.findAllByExpenseDateBetween(startIntervalDate, endIntervalDate)

    fun addExpense(expense: Expense): Expense {
        return expenseRepository.save(expense)
    }

    fun deleteExpense(expenseId: Int) {
        val expense = expenseRepository.getById(expenseId)
        return expenseRepository.delete(expense)
    }

    fun deleteExpensesByLabelId(labelId: Int) = expenseRepository.deleteExpensesByLabelId(labelId)
}
