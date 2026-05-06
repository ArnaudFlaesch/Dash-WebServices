package com.cashmanager.infra.entity

import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import java.time.LocalDate

data class TotalExpenseByMonthEntity(
    val total: Float,
    val date: LocalDate
) {
    fun toDomain() = TotalExpenseByMonthDomain(total = this.total, date = this.date)
}
