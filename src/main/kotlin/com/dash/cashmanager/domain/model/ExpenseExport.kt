package com.dash.cashmanager.domain.model

data class ExpenseExport(
    val id: Int,
    val amount: Float,
    val expenseDate: String,
    val labelId: Int
)
