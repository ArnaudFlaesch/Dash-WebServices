package com.dash.repository

import com.dash.entity.Widget
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WidgetRepository : JpaRepository<Widget, Long> {
    fun findByTabIdOrderByWidgetOrderAsc(tabId: Int): List<Widget>
}
