package com.dash.infra.repository

import com.dash.infra.entity.workoutwidget.WorkoutTypeEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WorkoutTypeRepository : JpaRepository<WorkoutTypeEntity, Int> {
    fun findByUserIdOrderByNameAsc(userId: Int): List<WorkoutTypeEntity>
}
