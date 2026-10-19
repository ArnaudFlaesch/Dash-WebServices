package com.cashmanager.infra.repository

import com.cashmanager.infra.entity.LabelEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LabelRepository : JpaRepository<LabelEntity, Int> {
    fun findByUserIdOrderByLabel(userId: Int): List<LabelEntity>
}
