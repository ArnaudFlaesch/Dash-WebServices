package com.cashmanager.infra.entity

import com.cashmanager.domain.model.TotalExpenseByMonthDomain
import java.util.*

data class TotalExpenseByMonthEntity(
    val total: Float,
    val date: Date
) {
    fun toDomain() = TotalExpenseByMonthDomain(total = this.total, date = this.date)
}
