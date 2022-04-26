package com.cashmanager.model

data class ExpenseToDisplay(
    val id: Int = 0,
    val amount: Float = 0F,
    val expenseDate: String = "",
    val labelId: Int = 0
)
