package com.cashmanager.service

import com.cashmanager.entity.Label
import com.cashmanager.repository.LabelRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class LabelService {

    @Autowired
    private lateinit var labelRepository: LabelRepository

    @Autowired
    private lateinit var expenseService: ExpenseService

    fun getLabels(): List<Label> = labelRepository.findAll()

    fun addLabel(labelToAdd: Label): Label = labelRepository.save(labelToAdd)

    fun updateLabel(label: Label): Label = labelRepository.save(label)

    fun deleteLabel(labelId: Int) {
        expenseService.deleteExpensesByLabelId(labelId)
        val label = labelRepository.getById(labelId)
        return labelRepository.delete(label)
    }
}
