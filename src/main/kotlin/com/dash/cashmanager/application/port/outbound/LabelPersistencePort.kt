package com.dash.cashmanager.application.port.outbound

import com.dash.cashmanager.domain.model.Label

interface LabelPersistencePort {
    fun getLabels(userId: Int): List<Label>

    fun addLabel(
        labelToAdd: String,
        userId: Int
    ): Label

    fun updateLabel(labelToUpdate: Label): Label

    fun deleteLabel(labelId: Int)
}
