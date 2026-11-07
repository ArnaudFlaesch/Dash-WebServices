package com.dash.cashmanager.adapter.inbound.web.request

import java.time.LocalDate

data class InsertExpenseRequest(
    val amount: Float,
    val expenseDate: LocalDate,
    val labelId: Int
)
