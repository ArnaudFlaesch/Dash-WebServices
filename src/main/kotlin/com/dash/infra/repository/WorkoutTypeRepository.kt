package com.dash.infra.repository

import com.dash.infra.entity.workoutwidget.WorkoutTypeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutTypeRepository : JpaRepository<WorkoutTypeEntity, Int> {

    fun findByUserId(userId: Int): List<WorkoutTypeEntity>
}
