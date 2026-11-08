package com.dash.cashmanager.adapter.outbound.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<LabelEntity, Int> {
    fun findByUserIdOrderByLabel(userId: Int): List<LabelEntity>
}
