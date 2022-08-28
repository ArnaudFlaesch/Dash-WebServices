package com.dash.controller.requests.workoutWidget

data class AddWorkoutExercisePayload(
    val workoutSessionId: Int,
    val workoutTypeId: Int,
    val numberOfReps: Int
)
