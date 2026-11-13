package com.dash.cashmanager.adapter.outbound.persistence

import com.dash.cashmanager.domain.model.Expense
import com.dash.cashmanager.domain.model.Label
import com.dash.cashmanager.domain.model.TotalExpenseByMonth

fun ExpenseEntity.toDomain(): Expense =
    Expense(
        id = this.id,
        amount = this.amount,
        expenseDate = this.expenseDate,
        labelId = label.id
    )

fun LabelEntity.toDomain(): Label = Label(this.id, this.label, this.user.id)

fun TotalExpenseByMonthEntity.toDomain(): TotalExpenseByMonth =
    TotalExpenseByMonth(total = this.total, date = this.date)
