package com.dash.infra.repository

import com.dash.infra.entity.WidgetEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WidgetRepository : JpaRepository<WidgetEntity, Int> {
    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<WidgetEntity>

    @Query("SELECT COUNT(*) FROM WidgetEntity WHERE tab.id = :tabId")
    fun getNumberOfWidgetsByTab(tabId: Int): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM WidgetEntity WHERE tab.id = :tabId")
    fun deleteWidgetsByTabId(tabId: Int)
}
