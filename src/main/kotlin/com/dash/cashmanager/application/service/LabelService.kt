package com.dash.cashmanager.application.service

import com.dash.cashmanager.application.port.inbound.LabelUseCase
import com.dash.cashmanager.application.port.outbound.ExpensePersistencePort
import com.dash.cashmanager.application.port.outbound.LabelPersistencePort
import com.dash.cashmanager.domain.model.Label
import com.dash.shared.application.port.outbound.CurrentUserPort
import com.dash.shared.application.security.SecurityConditions
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class LabelService(
    private val labelPersistencePort: LabelPersistencePort,
    private val expensePersistencePort: ExpensePersistencePort,
    private val currentUserPort: CurrentUserPort
) : LabelUseCase {
    override fun getUserLabels(): List<Label> = labelPersistencePort.getLabels(currentUserPort.currentUserId())

    override fun addLabel(labelToAdd: String): Label =
        labelPersistencePort.addLabel(labelToAdd, currentUserPort.currentUserId())

    override fun updateLabel(labelToUpdate: Label): Label = labelPersistencePort.updateLabel(labelToUpdate)

    @Transactional
    override fun deleteLabel(labelId: Int) {
        expensePersistencePort.deleteExpensesByLabelId(labelId)
        labelPersistencePort.deleteLabel(labelId)
    }
}
