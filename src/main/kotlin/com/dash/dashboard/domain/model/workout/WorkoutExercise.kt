package com.dash.dashboard.domain.model.workout

data class WorkoutExercise(
    val workoutSessionId: Int,
    val workoutTypeId: Int,
    val numberOfReps: Int
)
