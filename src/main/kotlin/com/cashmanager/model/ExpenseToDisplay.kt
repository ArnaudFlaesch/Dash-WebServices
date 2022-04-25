package com.cashmanager.model

data class ExpenseToDisplay(
    val id: Int,
    val amount: Float,
    val expenseDate: String,
    val labelId: Int
)
