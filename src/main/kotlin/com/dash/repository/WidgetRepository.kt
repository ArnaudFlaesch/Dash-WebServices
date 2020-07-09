package com.dash.repository

import com.dash.entity.Widget
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface WidgetRepository : JpaRepository<Widget, Int> {
    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<Widget>

    @Query("SELECT COUNT(*) FROM Widget WHERE tab_id = :tabId")
    fun getNumberOfWidgetsByTab(tabId: Int): Int

    @Transactional
    @Modifying
    @Query("DELETE FROM Widget WHERE tab_id = :tabId")
    fun deleteWidgetsByTabId(tabId: Int)
}
