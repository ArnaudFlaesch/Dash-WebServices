package com.cashmanager.domain.service

import com.cashmanager.domain.model.CashManagerImportData
import com.cashmanager.domain.model.ExpenseExportDomain
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
class CashManagerConfigService(
    private val labelService: LabelService,
    private val expenseService: ExpenseService
) {
    private val logger = LoggerFactory.getLogger(this::class.java)

    fun exportData(): Map<String, Any> {
        val expenses =
            expenseService.getUserExpenses().map { expense ->
                ExpenseExportDomain(expense.id, expense.amount, expense.expenseDate.toString(), expense.labelId)
            }
        return mapOf("expenses" to expenses, "labels" to labelService.getUserLabels())
    }

    /** Imports the labels and their expenses in a single transaction, so a failing import leaves no partial data. */
    @Transactional
    fun importData(importData: CashManagerImportData) {
        logger.info("Import commencé")
        val expensesByLabelId = importData.expenses.groupBy { it.labelId }
        importData.labels.forEach { label ->
            val insertedLabel = labelService.addLabel(label.label)
            expensesByLabelId[label.id].orEmpty().forEach { expense ->
                expenseService.addExpense(expense.amount, LocalDate.parse(expense.expenseDate), insertedLabel.id)
            }
        }
        logger.info("Import terminé")
    }
}
