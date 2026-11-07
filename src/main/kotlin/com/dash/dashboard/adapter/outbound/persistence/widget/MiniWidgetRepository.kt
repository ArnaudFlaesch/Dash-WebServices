package com.dash.dashboard.adapter.outbound.persistence.widget

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MiniWidgetRepository : JpaRepository<MiniWidgetEntity, Int> {
    fun findByUserId(userId: Int): List<MiniWidgetEntity>

    fun existsByIdAndUserId(
        id: Int,
        userId: Int
    ): Boolean
}
