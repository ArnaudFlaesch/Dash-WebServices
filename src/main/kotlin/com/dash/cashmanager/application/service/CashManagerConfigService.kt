package com.dash.cashmanager.application.service

import com.dash.cashmanager.application.port.inbound.CashManagerConfigUseCase
import com.dash.cashmanager.application.port.outbound.ExpensePersistencePort
import com.dash.cashmanager.application.port.outbound.LabelPersistencePort
import com.dash.cashmanager.domain.model.CashManagerExportData
import com.dash.cashmanager.domain.model.CashManagerImportData
import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.ExpenseExport
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.application.security.SecurityConditions
import org.slf4j.LoggerFactory
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Service
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class CashManagerConfigService(
    private val labelPersistencePort: LabelPersistencePort,
    private val expensePersistencePort: ExpensePersistencePort,
    private val currentUserPort: CurrentUserPort
) : CashManagerConfigUseCase {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun exportData(): CashManagerExportData =
        currentUserPort.currentUserId().let { userId ->
            CashManagerExportData(
                expenses =
                    expensePersistencePort.getAllUserExpenses(userId).map { expense ->
                        ExpenseExport(expense.id, expense.amount, expense.expenseDate.toString(), expense.labelId)
                    },
                labels = labelPersistencePort.getLabels(userId)
            )
        }

    /** Imports the labels and their expenses in a single transaction, so a failing import leaves no partial data. */
    @Transactional
    override fun importData(importData: CashManagerImportData): List<Expense> =
        importData.expenses
            .groupBy(ExpenseExport::labelId)
            .let { expensesByLabelId ->
                val userId = currentUserPort.currentUserId()
                importData.labels.flatMap { label ->
                    val insertedLabel = labelPersistencePort.addLabel(label.label, userId)
                    expensesByLabelId[label.id].orEmpty().map { expense ->
                        expensePersistencePort.insertExpense(
                            Expense(0, expense.amount, LocalDate.parse(expense.expenseDate), insertedLabel.id)
                        )
                    }
                }
            }.also { logger.info("Import terminé : {} dépenses importées", it.size) }
}
