package com.dash.dashboard.application.port.inbound

import com.dash.dashboard.domain.model.workout.*
import java.time.LocalDate

interface WorkoutWidgetUseCase {
    fun getWorkoutTypes(): List<WorkoutType>

    fun getWorkoutSessions(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutSession>

    fun getWorkoutStatsByPeriod(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByInterval>

    fun getWorkoutStatsByMonth(
        dateIntervalStart: LocalDate,
        dateIntervalEnd: LocalDate
    ): List<WorkoutStatsByMonth>

    fun addWorkoutType(workoutType: String): WorkoutType

    fun getWorkoutExercises(workoutSessionId: Int): List<WorkoutExercise>

    fun updateWorkoutExercise(
        workoutSessionId: Int,
        workoutTypeId: Int,
        numberOfReps: Int
    ): WorkoutExercise

    fun createWorkoutSession(workoutDate: LocalDate): WorkoutSession
}
