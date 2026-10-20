package com.dash.infra.repository

import com.dash.infra.entity.workoutwidget.WorkoutSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WorkoutSessionRepository : JpaRepository<WorkoutSessionEntity, Int> {
    fun findByUserIdAndWorkoutDateBetweenOrderByWorkoutDateAsc(
        userId: Int,
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutSessionEntity>
}
