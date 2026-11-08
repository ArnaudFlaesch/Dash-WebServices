package com.dash.cashmanager.adapter.outbound.persistence

import com.dash.cashmanager.domain.model.TotalExpenseByMonth
import java.time.LocalDate

data class TotalExpenseByMonthEntity(
    val total: Float,
    val date: LocalDate
)
