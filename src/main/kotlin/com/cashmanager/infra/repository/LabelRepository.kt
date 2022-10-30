package com.cashmanager.infra.repository

import com.cashmanager.infra.entity.LabelEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface LabelRepository : JpaRepository<LabelEntity, Int> {

    fun findByUserId(userId: Int): List<LabelEntity>
}
