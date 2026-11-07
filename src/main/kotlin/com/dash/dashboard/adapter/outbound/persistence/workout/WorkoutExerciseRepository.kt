package com.dash.dashboard.adapter.outbound.persistence.workout

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface WorkoutExerciseRepository : JpaRepository<WorkoutExerciseEntity, WorkoutExerciseEntityId> {
    fun findAllByWorkoutExerciseIdWorkoutSessionId(workoutSessionId: Int): List<WorkoutExerciseEntity>

    @Query(name = "getWorkoutStatsByInterval", nativeQuery = true)
    fun getWorkoutStatsByInterval(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByIntervalEntity>

    @Query(name = "getWorkoutStatsByMonth", nativeQuery = true)
    fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByMonthEntity>
}
