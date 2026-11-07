package com.dash.domain.model.workoutwidget

import java.time.LocalDate

data class WorkoutStatsByMonthDomain(
    val totalNumberOfReps: Long,
    val workoutTypeId: Int,
    val monthPeriod: LocalDate,
    val workoutTypeName: String
)
