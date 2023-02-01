package com.dash.infra.entity.workoutwidget

import com.dash.domain.model.workoutwidget.WorkoutStatsByIntervalDomain

data class WorkoutStatsByIntervalEntity(
    val totalNumberOfReps: Long,
    val workoutTypeName: String
) {
    fun toDomain() = WorkoutStatsByIntervalDomain(totalNumberOfReps = this.totalNumberOfReps, workoutTypeName = this.workoutTypeName)
}
