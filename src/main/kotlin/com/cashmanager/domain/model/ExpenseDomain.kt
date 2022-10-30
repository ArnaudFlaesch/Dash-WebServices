package com.cashmanager.domain.model

import java.time.LocalDate

data class ExpenseDomain(
    val id: Int,
    val amount: Float,
    val expenseDate: LocalDate,
    val labelId: Int
)
