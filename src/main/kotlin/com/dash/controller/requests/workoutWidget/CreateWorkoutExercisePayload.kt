package com.dash.controller.requests.workoutWidget

data class CreateWorkoutExercisePayload(
    val workoutSessionId: Int,
    val workoutTypeId: Int,
    val numberOfReps: Int
)
