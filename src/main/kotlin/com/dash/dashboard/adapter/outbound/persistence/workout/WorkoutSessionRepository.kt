package com.dash.dashboard.adapter.outbound.persistence.workout

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

    fun existsByIdAndUserId(
        id: Int,
        userId: Int
    ): Boolean
}
