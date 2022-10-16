package com.dash.app.controller.requests.workoutWidget

import java.time.LocalDate

data class CreateWorkoutSessionPayload(
    val workoutDate: LocalDate,
    val userId: Int
)
