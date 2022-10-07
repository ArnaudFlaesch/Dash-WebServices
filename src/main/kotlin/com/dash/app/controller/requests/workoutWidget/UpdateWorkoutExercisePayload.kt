package com.dash.app.controller.requests.workoutWidget

data class UpdateWorkoutExercisePayload(
    val workoutSessionId: Int,
    val workoutTypeId: Int,
    val numberOfReps: Int
)
