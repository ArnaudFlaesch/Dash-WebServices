package com.dash.infra.repository

import com.dash.infra.entity.MiniWidgetEntity
import org.springframework.data.jpa.repository.JpaRepository

interface MiniWidgetRepository : JpaRepository<MiniWidgetEntity, Int> {
    fun findByUserId(userId: Int): List<MiniWidgetEntity>
}
