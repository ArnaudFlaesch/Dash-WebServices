package com.dash.infra.entity.workoutwidget

import com.dash.domain.model.workoutwidget.WorkoutStatsByMonthDomain

data class WorkoutStatsByMonthEntity(
    val totalNumberOfReps: Number,
    val workoutTypeName: String
) {
    fun toDomain() = WorkoutStatsByMonthDomain(totalNumberOfReps = this.totalNumberOfReps, workoutTypeName = this.workoutTypeName)
}
