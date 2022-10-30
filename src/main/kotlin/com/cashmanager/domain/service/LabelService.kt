package com.cashmanager.domain.service

import com.cashmanager.domain.model.LabelDomain
import com.cashmanager.infra.adapter.LabelPersistenceAdapter
import com.common.domain.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LabelService {

    @Autowired
    private lateinit var labelPersistenceAdapter: LabelPersistenceAdapter

    @Autowired
    private lateinit var expenseService: ExpenseService

    @Autowired
    private lateinit var userService: UserService

    fun getLabels(): List<LabelDomain> {
        val authenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return labelPersistenceAdapter.getLabels(authenticatedUserId)
    }

    fun addLabel(labelToAdd: String): LabelDomain {
        val currentAuthenticatedUserId = userService.getCurrentAuthenticatedUserId()
        return labelPersistenceAdapter.addLabel(labelToAdd, currentAuthenticatedUserId)
    }

    fun updateLabel(labelToUpdate: LabelDomain): LabelDomain =
        labelPersistenceAdapter.updateLabel(labelToUpdate)

    fun deleteLabel(labelId: Int) {
        expenseService.deleteExpensesByLabelId(labelId)
        return labelPersistenceAdapter.deleteLabel(labelId)
    }
}
