package com.cashmanager.controller

import com.cashmanager.entity.Expense
import com.cashmanager.service.ExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = ["*"])
class ExpenseController {

    @Autowired
    private lateinit var expenseService: ExpenseService

    @GetMapping("/")
    fun getTabs(): List<Expense> = (expenseService.getAllExpenses())

    @PostMapping("/addExpense")
    fun addTab(@RequestBody expense: Expense): Expense = expenseService.addExpense(expense)

    @DeleteMapping("/deleteExpense")
    fun deleteTab(@RequestParam(value = "id") id: Int) = expenseService.deleteExpensesByTabId(id)
}
