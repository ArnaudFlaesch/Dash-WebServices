package com.cashmanager.controller.requests

import java.time.LocalDate

data class InsertExpensePayload(
    val amount: Float,
    val expenseDate: LocalDate,
    val labelId: Int,
)
