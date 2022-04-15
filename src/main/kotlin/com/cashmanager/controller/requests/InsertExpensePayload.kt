package com.cashmanager.controller.requests

import java.time.LocalDate

data class InsertExpensePayload(
    val amount: Int,
    val expenseDate: LocalDate,
    val labelId: Int,
)
