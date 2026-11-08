package com.dash.cashmanager.application.port.inbound

import com.dash.cashmanager.domain.model.Label

interface LabelUseCase {
    fun getUserLabels(): List<Label>

    fun addLabel(labelToAdd: String): Label

    fun updateLabel(labelToUpdate: Label): Label

    /** Deletes the label and its expenses. */
    fun deleteLabel(labelId: Int)
}
