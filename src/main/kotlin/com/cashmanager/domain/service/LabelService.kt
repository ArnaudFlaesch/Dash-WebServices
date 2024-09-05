package com.cashmanager.domain.service

import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.infra.adapter.LabelPersistenceAdapter
import com.common.app.security.SecurityConditions
import com.common.domain.service.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service

@Service
@PreAuthorize(SecurityConditions.IS_USER_ADMIN)
class LabelService(
    private val labelPersistenceAdapter: LabelPersistenceAdapter,
    private val expenseService: ExpenseService,
    private val userService: UserService
) {
    fun getUserLabels(): List<LabelDomain> =
        labelPersistenceAdapter.getLabels(userService.getCurrentAuthenticatedUserId())

    fun addLabel(labelToAdd: String): LabelDomain =
        labelPersistenceAdapter.addLabel(labelToAdd, userService.getCurrentAuthenticatedUserId())

    fun updateLabel(labelToUpdate: LabelDomain): LabelDomain = labelPersistenceAdapter.updateLabel(labelToUpdate)

    fun deleteLabel(labelId: Int) =
        expenseService
            .deleteExpensesByLabelId(labelId)
            .let { _ -> labelPersistenceAdapter.deleteLabel(labelId) }
}
