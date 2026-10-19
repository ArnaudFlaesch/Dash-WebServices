package com.dash.infra.repository

import com.dash.infra.entity.workoutwidget.WorkoutSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDate

interface WorkoutSessionRepository : JpaRepository<WorkoutSessionEntity, Int> {
    fun findByUserIdAndWorkoutDateBetweenOrderByWorkoutDateAsc(
        userId: Int,
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutSessionEntity>
}
