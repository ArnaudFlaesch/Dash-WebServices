package com.cashmanager.repository

import com.cashmanager.entity.Expense
import com.dash.entity.Widget
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface ExpenseRepository : JpaRepository<Expense, Int> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Expense WHERE label_id = :labelId")
    fun deleteExpensesByLabelId(labelId: Int)
}
