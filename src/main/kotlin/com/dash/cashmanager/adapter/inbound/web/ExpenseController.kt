package com.dash.cashmanager.adapter.inbound.web

import com.dash.cashmanager.adapter.inbound.web.request.InsertExpenseRequest
import com.dash.cashmanager.application.port.inbound.ExpenseUseCase
import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/expenses")
class ExpenseController(
    private val expenseUseCase: ExpenseUseCase
) {
    @GetMapping
    fun getExpenses(
        @RequestParam("startIntervalDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        startIntervalDate: LocalDate,
        @RequestParam("endIntervalDate")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        endIntervalDate: LocalDate
    ): List<Expense> = expenseUseCase.getExpensesByInterval(startIntervalDate, endIntervalDate)

    @GetMapping("/monthly-totals")
    fun getTotalExpensesByMonth(
        @RequestParam(required = false) labelId: Int?
    ): List<TotalExpenseByMonth> =
        labelId
            ?.let(expenseUseCase::getTotalExpensesByMonthByLabelId)
            ?: expenseUseCase.getTotalExpensesByMonth()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addExpense(
        @RequestBody expenseRequest: InsertExpenseRequest
    ): Expense =
        expenseUseCase.addExpense(
            expenseRequest.amount,
            expenseRequest.expenseDate,
            expenseRequest.labelId
        )

    @PutMapping("/{expenseId}")
    fun updateExpense(
        @PathVariable expenseId: Int,
        @RequestBody expense: Expense
    ): Expense = expenseUseCase.updateExpense(expense.copy(id = expenseId))

    @DeleteMapping("/{expenseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteExpense(
        @PathVariable expenseId: Int
    ) {
        expenseUseCase.deleteExpense(expenseId)
    }
}
