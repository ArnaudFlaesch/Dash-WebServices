package com.dash.cashmanager.adapter.outbound.persistence

import java.time.LocalDate

data class TotalExpenseByMonthEntity(
    val total: Float,
    val date: LocalDate
)
