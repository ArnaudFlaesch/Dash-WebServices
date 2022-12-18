package com.dash.infra.repository

import com.dash.infra.entity.MiniWidgetEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MiniWidgetRepository : JpaRepository<MiniWidgetEntity, Int> {
    fun findByUserId(userId: Int): List<MiniWidgetEntity>
}
