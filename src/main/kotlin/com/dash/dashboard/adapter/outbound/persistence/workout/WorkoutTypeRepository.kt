package com.dash.dashboard.adapter.outbound.persistence.workout

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutTypeRepository : JpaRepository<WorkoutTypeEntity, Int> {
    fun findByUserIdOrderByNameAsc(userId: Int): List<WorkoutTypeEntity>

    fun existsByIdAndUserId(
        id: Int,
        userId: Int
    ): Boolean
}
