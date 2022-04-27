package com.cashmanager.repository

import com.cashmanager.entity.Expense
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate
import javax.transaction.Transactional

@Repository
interface ExpenseRepository : JpaRepository<Expense, Int> {

    fun findAllByExpenseDateBetween(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<Expense>

    @Transactional
    @Modifying
    @Query("DELETE FROM Expense WHERE label_id = :labelId")
    fun deleteExpensesByLabelId(labelId: Int)
}
