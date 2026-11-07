package com.dash.cashmanager.domain.model

import java.time.LocalDate

data class TotalExpenseByMonth(
    val total: Float,
    val date: LocalDate
)
