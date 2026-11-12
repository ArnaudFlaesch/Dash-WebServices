package com.dash.cashmanager.application.service

import com.dash.cashmanager.application.port.inbound.CashManagerConfigUseCase
import com.dash.cashmanager.application.port.outbound.ExpensePersistencePort
import com.dash.cashmanager.application.port.outbound.LabelPersistencePort
import com.dash.cashmanager.domain.model.CashManagerExportData
import com.dash.cashmanager.domain.model.CashManagerImportData
import com.dash.cashmanager.domain.model.Expense
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.application.security.SecurityConditions
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class CashManagerConfigService(
    private val labelPersistencePort: LabelPersistencePort,
    private val expensePersistencePort: ExpensePersistencePort,
    private val currentUserPort: CurrentUserPort
) : CashManagerConfigUseCase {
    override fun exportData(): CashManagerExportData =
        currentUserPort.currentUserId().let { userId ->
            CashManagerExportData.of(
                expenses = expensePersistencePort.getAllUserExpenses(userId),
                labels = labelPersistencePort.getLabels(userId)
            )
        }

    /** Imports the labels and their expenses in a single transaction, so a failing import leaves no partial data. */
    @Transactional
    override fun importData(importData: CashManagerImportData): List<Expense> {
        val userId = currentUserPort.currentUserId()
        return importData.labelsWithExpenses().flatMap { (label, expenses) ->
            val importedLabel = labelPersistencePort.addLabel(label.label, userId)
            expenses
                .map { expense -> expense.toNewExpense(importedLabel.id) }
                .map(expensePersistencePort::insertExpense)
        }
    }
}
