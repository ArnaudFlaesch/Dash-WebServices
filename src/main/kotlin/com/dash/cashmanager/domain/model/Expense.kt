package com.dash.cashmanager.domain.model

import java.time.LocalDate

data class Expense(
    val id: Int,
    val amount: Float,
    val expenseDate: LocalDate,
    val labelId: Int
)
