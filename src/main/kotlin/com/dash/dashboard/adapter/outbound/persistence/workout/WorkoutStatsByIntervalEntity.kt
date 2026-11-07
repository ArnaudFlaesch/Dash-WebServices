package com.dash.dashboard.adapter.outbound.persistence.workout

import com.dash.dashboard.domain.model.workout.WorkoutStatsByInterval

data class WorkoutStatsByIntervalEntity(
    val totalNumberOfReps: Long,
    val workoutTypeName: String
) {
    fun toDomain(): WorkoutStatsByInterval =
        WorkoutStatsByInterval(
            totalNumberOfReps = this.totalNumberOfReps,
            workoutTypeName = this.workoutTypeName
        )
}
