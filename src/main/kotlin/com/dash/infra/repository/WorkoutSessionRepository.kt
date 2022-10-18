package com.dash.infra.repository

import com.dash.infra.entity.workoutwidget.WorkoutSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface WorkoutSessionRepository : JpaRepository<WorkoutSessionEntity, Int> {

    fun findByUserId(userId: Int): List<WorkoutSessionEntity>
}
