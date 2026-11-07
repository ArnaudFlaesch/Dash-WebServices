package com.dash.dashboard.application.port.outbound

import com.dash.dashboard.domain.model.workout.*
import java.time.LocalDate

interface WorkoutPersistencePort {
    fun getWorkoutTypes(userId: Int): List<WorkoutType>

    fun getWorkoutSessions(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutSession>

    fun getWorkoutStatsByPeriod(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByInterval>

    fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate,
        userId: Int
    ): List<WorkoutStatsByMonth>

    fun getWorkoutExercises(workoutSessionId: Int): List<WorkoutExercise>

    fun addWorkoutType(
        name: String,
        userId: Int
    ): WorkoutType

    fun saveWorkoutExercise(workoutExercise: WorkoutExercise): WorkoutExercise

    fun createWorkoutSession(
        workoutDate: LocalDate,
        userId: Int
    ): WorkoutSession

    fun isWorkoutSessionOwnedBy(
        workoutSessionId: Int,
        userId: Int
    ): Boolean

    fun isWorkoutTypeOwnedBy(
        workoutTypeId: Int,
        userId: Int
    ): Boolean
}
