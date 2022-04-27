package com.cashmanager.controller

import com.cashmanager.controller.requests.InsertExpensePayload
import com.cashmanager.entity.Expense
import com.cashmanager.service.ExpenseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import java.util.*

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = ["*"])
class ExpenseController {

    @Autowired
    private lateinit var expenseService: ExpenseService

    @GetMapping("/")
    fun getExpenses(
        @RequestParam("startIntervalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) startIntervalDate: LocalDate,
        @RequestParam("endIntervalDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) endIntervalDate: LocalDate
    ): List<Expense> = (expenseService.getExpensesByInterval(startIntervalDate, endIntervalDate))

    @PostMapping("/addExpense")
    fun addExpense(@RequestBody expense: InsertExpensePayload): Expense = expenseService.addExpense(expense)

    @PatchMapping("/updateExpense")
    fun updateExpense(@RequestBody expense: Expense): Expense = expenseService.updateExpense(expense)

    @DeleteMapping("/deleteExpense")
    fun deleteExpense(@RequestParam(value = "expenseId") id: Int) = expenseService.deleteExpense(id)
}
