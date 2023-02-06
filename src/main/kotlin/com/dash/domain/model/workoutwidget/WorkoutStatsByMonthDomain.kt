package com.dash.domain.model.workoutwidget

import java.time.LocalDate

data class WorkoutStatsByMonthDomain(
    val totalNumberOfReps: Number,
    val workoutTypeId: Number,
    val monthPeriod: LocalDate,
    val workoutTypeName: String
)
