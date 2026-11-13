package com.dash.cashmanager.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDate

class CashManagerDomainTests {
    @Test
    fun shouldConvertExpensesForExportAndBack() {
        val expense = Expense(3, 12.5f, LocalDate.of(2026, 2, 14), 7)
        val exported = expense.toExport()

        assertEquals(ExpenseExport(3, 12.5f, "2026-02-14", 7), exported)
        assertEquals(Expense(0, 12.5f, LocalDate.of(2026, 2, 14), 9), exported.toNewExpense(9))
    }

    @Test
    fun shouldGroupImportedExpensesByLabelInLabelOrder() {
        val food = LabelExport(1, "Food")
        val rent = LabelExport(2, "Rent")
        val rentExpense = ExpenseExport(1, 800f, "2026-01-01", 2)

        val labelsWithExpenses =
            CashManagerImportData(expenses = listOf(rentExpense), labels = listOf(food, rent)).labelsWithExpenses()

        assertEquals(listOf(food to emptyList(), rent to listOf(rentExpense)), labelsWithExpenses)
    }
}
