package com.dash.dashboard.adapter.inbound.web.request.workout

data class UpdateWorkoutExerciseRequest(
    val workoutSessionId: Int,
    val workoutTypeId: Int,
    val numberOfReps: Int
)
