package com.cashmanager.model

import com.cashmanager.entity.Expense
import com.cashmanager.entity.Label

data class ImportData(
    val expenses: List<Expense> = listOf(),

    val labels: List<Label> = listOf()
)
