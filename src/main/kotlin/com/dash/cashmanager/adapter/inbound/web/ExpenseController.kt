package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.adapter.inbound.web.request.InsertExpenseRequest
import com.dash.cashmanager.application.port.inbound.ExpenseUseCase
import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/expense")
class ExpenseController(
    private val expenseUseCase: ExpenseUseCase
) {
    @GetMapping("/")
    fun getExpenses(
        @RequestParam("startIntervalDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        startIntervalDate: LocalDate,
        @RequestParam("endIntervalDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        endIntervalDate: LocalDate
    ): List<Expense> = expenseUseCase.getExpensesByInterval(startIntervalDate, endIntervalDate)

    @GetMapping("/getTotalExpensesByMonth")
    fun getTotalExpensesByMonth(): List<TotalExpenseByMonth> = expenseUseCase.getTotalExpensesByMonth()

    @GetMapping("/getTotalExpensesByMonthByLabelId")
    fun getTotalExpensesByMonthByLabelId(
        @RequestParam labelId: Int
    ): List<TotalExpenseByMonth> = expenseUseCase.getTotalExpensesByMonthByLabelId(labelId)

    @PostMapping("/addExpense")
    fun addExpense(
        @RequestBody expenseRequest: InsertExpenseRequest
    ): Expense =
        expenseUseCase.addExpense(
            expenseRequest.amount,
            expenseRequest.expenseDate,
            expenseRequest.labelId
        )

    @PatchMapping("/updateExpense")
    fun updateExpense(
        @RequestBody expense: Expense
    ): Expense = expenseUseCase.updateExpense(expense)

    @DeleteMapping("/deleteExpense")
    fun deleteExpense(
        @RequestParam(value = "expenseId") id: Int
    ) {
        expenseUseCase.deleteExpense(id)
    }
}
