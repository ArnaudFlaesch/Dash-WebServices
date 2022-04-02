package com.cashmanager.service

import com.cashmanager.entity.Expense
import com.cashmanager.repository.ExpenseRepository
import com.dash.controller.requests.UpdateWidgetDataPayload
import com.dash.entity.Widget
import com.dash.repository.WidgetRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ExpenseService {

    @Autowired
    private lateinit var expenseRepository: ExpenseRepository

    fun getAllExpenses(): List<Expense> = expenseRepository.findAll()

    fun addExpense(expense: Expense): Expense {
        return expenseRepository.save(expense)
    }

    fun deleteExpensesByTabId(id: Int) = expenseRepository.deleteExpensesByLabelId(id)
}
