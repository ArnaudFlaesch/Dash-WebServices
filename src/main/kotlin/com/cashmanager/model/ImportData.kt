package com.cashmanager.model

import com.cashmanager.entity.Label

data class ImportData(
    val expenses: List<ExpenseToDisplay> = listOf(),

    val labels: List<Label> = listOf()
)
