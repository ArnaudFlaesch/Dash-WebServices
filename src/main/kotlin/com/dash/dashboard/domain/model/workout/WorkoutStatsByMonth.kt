package com.dash.dashboard.domain.model.workout

import java.time.LocalDate

data class WorkoutStatsByMonth(
    val totalNumberOfReps: Long,
    val workoutTypeId: Int,
    val monthPeriod: LocalDate,
    val workoutTypeName: String
)
