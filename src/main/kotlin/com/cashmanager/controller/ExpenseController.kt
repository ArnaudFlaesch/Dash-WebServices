package com.cashmanager.controller

import com.cashmanager.entity.Expense
import com.cashmanager.service.ExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = ["*"])
class ExpenseController {

    @Autowired
    private lateinit var expenseService: ExpenseService

    @GetMapping("/")
    fun getExpenses(
        @RequestParam("startIntervalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) startIntervalDate: Date,
        @RequestParam("endIntervalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) endIntervalDate: Date
    ): List<Expense> = (expenseService.getExpensesByInterval(startIntervalDate, endIntervalDate))

    @PostMapping("/addExpense")
    fun addExpense(@RequestBody expense: Expense): Expense = expenseService.addExpense(expense)

    @DeleteMapping("/deleteExpense")
    fun deleteExpense(@RequestParam(value = "expenseId") id: Int) = expenseService.deleteExpense(id)

    @DeleteMapping("/deleteExpenses")
    fun deleteExpenses(@RequestParam(value = "labelId") labelId: Int) = expenseService.deleteExpensesByLabelId(labelId)
}
