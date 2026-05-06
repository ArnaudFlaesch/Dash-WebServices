package com.cashmanager.domain.model

import java.time.LocalDate

data class TotalExpenseByMonthDomain(
    val total: Float,
    val date: LocalDate
)
