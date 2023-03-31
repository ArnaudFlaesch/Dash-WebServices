package com.cashmanager.app.controller

import com.cashmanager.app.controller.requests.InsertExpensePayload
import com.cashmanager.domain.model.ExpenseDomain
import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import com.cashmanager.domain.service.ExpenseService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/expense")
@CrossOrigin(origins = ["*"])
class ExpenseController(private val expenseService: ExpenseService) {

    @GetMapping("/")
    fun getExpenses(
        @RequestParam("startIntervalDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        startIntervalDate: LocalDate,
        @RequestParam("endIntervalDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        endIntervalDate: LocalDate
    ): List<ExpenseDomain> = (expenseService.getExpensesByInterval(startIntervalDate, endIntervalDate))

    @GetMapping("/getTotalExpensesByMonth")
    fun getTotalExpensesByMonth(): List<TotalExpenseByMonthDomain> = expenseService.getTotalExpensesByMonth()

    @GetMapping("/getTotalExpensesByMonthByLabelId")
    fun getTotalExpensesByMonthByLabelId(@RequestParam("labelId") labelId: Int): List<TotalExpenseByMonthDomain> =
        expenseService.getTotalExpensesByMonthByLabelId(labelId)

    @PostMapping("/addExpense")
    fun addExpense(@RequestBody expensePayload: InsertExpensePayload): ExpenseDomain =
        expenseService.addExpense(expensePayload.amount, expensePayload.expenseDate, expensePayload.labelId)

    @PatchMapping("/updateExpense")
    fun updateExpense(@RequestBody expense: ExpenseDomain): ExpenseDomain = expenseService.insertExpense(expense)

    @DeleteMapping("/deleteExpense")
    fun deleteExpense(@RequestParam(value = "expenseId") id: Int) = expenseService.deleteExpense(id)
}
