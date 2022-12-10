package com.cashmanager.infra.repository

import com.cashmanager.infra.entity.ExpenseEntity
import com.cashmanager.infra.entity.TotalExpenseByMonthEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface ExpenseRepository : JpaRepository<ExpenseEntity, Int> {

    fun findAllByExpenseDateBetweenOrderByExpenseDateAsc(startIntervalDate: LocalDate, endIntervalDate: LocalDate): List<ExpenseEntity>

    fun findAllByLabelUserId(userId: Int): List<ExpenseEntity>

    @Query(name = "getExpensesByMonth", nativeQuery = true)
    fun getTotalExpensesByMonth(userId: Int): List<TotalExpenseByMonthEntity>

    @Query(name = "getExpensesByMonthByLabelId", nativeQuery = true)
    fun getTotalExpensesByMonthByLabelId(labelId: Int, userId: Int): List<TotalExpenseByMonthEntity>

    @Transactional
    @Modifying
    @Query("DELETE FROM ExpenseEntity WHERE label.id = :labelId")
    fun deleteExpensesByLabelId(labelId: Int)
}
